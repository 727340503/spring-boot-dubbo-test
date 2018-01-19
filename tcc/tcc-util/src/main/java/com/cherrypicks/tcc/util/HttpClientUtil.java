package com.cherrypicks.tcc.util;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final int MAX_TOTAL = 200; // 最大连接数
    private static final int MAX_ROUTE_TOTAL = 20; // 每个路由基础的连接数
    private static final int CONNECT_TIMEOUT = 4000; // 连接超时毫秒
    private static final int REQUESTCONNECT_TIMEOUT = 3000;
    private static final int SOCKET_TIMEOUT = 10000; // 传输超时毫秒

    private static Boolean IGNORE_SSL = true;
    private static HttpClientUtil instance = null;
    private static PoolingHttpClientConnectionManager connManager  = null;
    private static CloseableHttpClient httpClient = null;

	static {

		final ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		final LayeredConnectionSocketFactory sslsf = createSSLConnSocketFactory();
		final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("https", sslsf)
				.register("http", plainsf).build();
		connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connManager.setMaxTotal(MAX_TOTAL);
		connManager.setDefaultMaxPerRoute(MAX_ROUTE_TOTAL);
		// 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
		connManager.setValidateAfterInactivity(30000);
		// 设置socket超时时间
		final SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
		connManager.setDefaultSocketConfig(socketConfig);

		final RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT).build();
		final HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(final IOException exception,
					final int executionCount, final HttpContext context) {
				if (executionCount >= 3) {// 如果已经重试了3次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return true;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					return false;
				}
				final HttpClientContext clientContext = HttpClientContext.adapt(context);
				final HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		httpClient = HttpClients.custom().setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig).setRetryHandler(httpRequestRetryHandler).build();
		if (connManager != null && connManager.getTotalStats() != null) {
			logger.info("now client pool " + connManager.getTotalStats().toString());
		}

	}

    private HttpClientUtil() {

    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

	// SSL的socket工厂创建
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			final SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, new TrustStrategy() {
						@Override
						public boolean isTrusted(
								final X509Certificate[] x509CertChain,
								final String authType)
								throws CertificateException {
							return true;
						}
					}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext);
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sslsf;
	}

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     */
    public String sendHttpPost(final String httpUrl) {
        final HttpPost httpPost = new HttpPost(httpUrl); // 创建httpPost

        if (isHttps(httpUrl)) {
            return sendHttpsPost(httpPost, IGNORE_SSL);
        } else {
            return sendHttpPost(httpPost);
        }
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(final String httpUrl, final String params) {
        final HttpPost httpPost = new HttpPost(httpUrl); // 创建httpPost
        try {
            // 设置参数
            final StringEntity stringEntity = new StringEntity(params, Constants.UTF8);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (isHttps(httpUrl)) {
            return sendHttpsPost(httpPost, IGNORE_SSL);
        } else {
            return sendHttpPost(httpPost);
        }
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     */
    public String sendHttpPost(final String httpUrl, final Map<String, String> maps) {
        final HttpPost httpPost = new HttpPost(httpUrl); // 创建httpPost
        // 创建参数队列
        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (final Entry<String, String> entry : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Constants.UTF8));
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (isHttps(httpUrl)) {
            return sendHttpsPost(httpPost, IGNORE_SSL);
        } else {
            return sendHttpPost(httpPost);
        }
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     */
    public String sendHttpPost(final String httpUrl, final Map<String, String> maps, final List<File> fileLists) {
        final HttpPost httpPost = new HttpPost(httpUrl); // 创建httpPost
        final MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (final Entry<String, String> entry : maps.entrySet()) {
            meBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
        }
        for (final File file : fileLists) {
            final FileBody fileBody = new FileBody(file);
            meBuilder.addPart("files", fileBody);
        }
        final HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);

        if (isHttps(httpUrl)) {
            return sendHttpsPost(httpPost, IGNORE_SSL);
        } else {
            return sendHttpPost(httpPost);
        }
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(final HttpPost httpPost) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);

            // Examine the response status
            examineStatus(response.getStatusLine().getStatusCode());
            entity = response.getEntity();
            if (null != entity) {
            	responseContent = EntityUtils.toString(entity, Constants.UTF8);
            	logger.info("Execute the request to complete：" + responseContent);
    	        EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpPost != null) {
           	 	httpPost.releaseConnection();
            }
        }
        return responseContent;
    }

    /**
     * 发送Post请求 https
     *
     * @param httpPost
     * @return
     */
    private String sendHttpsPost(final HttpPost httpPost, final boolean ignoreSsl) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            if (ignoreSsl) {
            	client = httpClient;
            } else {
            	client = (CloseableHttpClient) buildSslHttpClient(httpPost.getURI().toString());
            }
            // 执行请求
            response = client.execute(httpPost);

            // Examine the response status
            examineStatus(response.getStatusLine().getStatusCode());
            entity = response.getEntity();

            if (null != entity) {
            	responseContent = EntityUtils.toString(entity, Constants.UTF8);
            	logger.info("Execute the request to complete：" + responseContent);
    	        EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpPost != null) {
            	httpPost.releaseConnection();
            }
        }
        return responseContent;
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public String sendHttpGet(final String httpUrl) {
        final HttpGet httpGet = new HttpGet(httpUrl); // 创建get请求
        logger.debug(httpGet.toString());

        if (isHttps(httpUrl)) {
            return sendHttpsGet(httpGet, IGNORE_SSL, null);
        } else {
            return sendHttpGet(httpGet, null);
        }
    }

    /**
     * 发送Get请求
     * @param deviceIp
     *
     * @param httpPost
     * @return
     */
    private String sendHttpGet(final HttpGet httpGet, final String deviceIp) {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {

            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            setIpHeader(httpGet, deviceIp);

            // 执行请求
            response = httpClient.execute(httpGet);

            // Examine the response status
            examineStatus(response.getStatusLine().getStatusCode());
            entity = response.getEntity();

            if (null != entity) {
            	 responseContent = EntityUtils.toString(entity, Constants.UTF8);
            	 logger.info("Execute the request to complete：" + responseContent);
                 EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpGet != null) {
            	httpGet.releaseConnection();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     * @param deviceIp
     *
     * @param httpPost
     * @return
     */
    private String sendHttpsGet(final HttpGet httpGet, final boolean ignoreSsl, final String deviceIp) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            if (ignoreSsl) {
                client = httpClient;
            } else {
            	client = (CloseableHttpClient) buildSslHttpClient(httpGet.getURI().toString());
            }
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            setIpHeader(httpGet, deviceIp);

            // 执行请求
            response = client.execute(httpGet);

            // Examine the response status
            examineStatus(response.getStatusLine().getStatusCode());
            entity = response.getEntity();

            if (null != entity) {
            	responseContent = EntityUtils.toString(entity, Constants.UTF8);
            	logger.info("Execute the request to complete：" + responseContent);
                EntityUtils.consume(entity);
           }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpGet != null) {
            	httpGet.releaseConnection();
            }
        }
        return responseContent;
    }

    private void setIpHeader(final HttpGet httpGet, final String deviceIp) {
        if (StringUtils.isNotBlank(deviceIp)) {
            httpGet.setHeader("X-Forwarded-For", deviceIp);
        }
    }

    public String sendJsonPost(final String httpUrl, final String jsonString) {
        final HttpPost httpPost = new HttpPost(httpUrl); // 创建httpPost
        try {
            // 设置参数
            final StringEntity stringEntity = new StringEntity(jsonString, Constants.UTF8);
            stringEntity.setContentType(Constants.CONTENT_TYPE_JSON);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Accept", Constants.CONTENT_TYPE_JSON);
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (isHttps(httpUrl)) {
            return sendHttpsPost(httpPost, IGNORE_SSL);
        } else {
            return sendHttpPost(httpPost);
        }
    }

    private boolean isHttps(final String httpUrl) {
        return httpUrl.toLowerCase().startsWith("https://");
    }

    private HttpClient buildSslHttpClient(final String httpUrl) throws MalformedURLException, IOException {
        final PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpUrl));
        final DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);

        return HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
    }

    private void examineStatus(final int statusCode) throws Exception {
        final int serverErrorIndex = 5; // 5xx error
        final int clientErrorIndex = 4; // 4xx error
        if (String.valueOf(statusCode).startsWith(String.valueOf(serverErrorIndex))
                || String.valueOf(statusCode).startsWith(String.valueOf(clientErrorIndex))) {
            throw new Exception("Execute a request failed: response status code " + statusCode);
        }
    }

    /**
     */
    public Map<String, Object> sendHttpGetOfHeader(final String httpUrl, final String httpMethod,
    		final Map<String, String> headerMap) throws Exception {
        final HttpGet httpGet = new HttpGet(httpUrl);
        for (final Entry<String, String> entry : headerMap.entrySet()) {
        	httpGet.setHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        final Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 创建默认的httpClient实例.
            if (IGNORE_SSL) {
            	client = httpClient;
            } else {
            	client = (CloseableHttpClient) buildSslHttpClient(httpGet.getURI().toString());
            }
            // 执行请求
            response = client.execute(httpGet);
            result.put("status", response.getStatusLine().getStatusCode());
            entity = response.getEntity();

            if (null != entity) {
            	result.put("response", EntityUtils.toString(entity, Constants.UTF8));
                EntityUtils.consume(entity);
           }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpGet != null) {
            	httpGet.releaseConnection();
            }
        }
        return result;
    }


    /**
     */
    public Map<String, Object> sendHttpHeadOfHeader(final String httpUrl, final String httpMethod,
    		final Map<String, String> headerMap) throws Exception {
        final HttpHead httpHead = new HttpHead(httpUrl);
        for (final Entry<String, String> entry : headerMap.entrySet()) {
        	httpHead.setHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse response = null;
        final Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 执行请求
            response = httpClient.execute(httpHead);

            result.put("status", response.getStatusLine().getStatusCode());
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }


    /**
     */
    public Map<String, Object> sendHttpPostOfHeader(final String httpUrl, final String httpMethod,
    		final Map<String, String> headerMap, final String jsonString) throws Exception {
     // 创建httpPost
        final HttpPost httpPost = new HttpPost(httpUrl);
        try {
            // 设置参数
            if (StringUtils.isNotBlank(jsonString)) {
                final StringEntity stringEntity = new StringEntity(jsonString);
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            }
            for (final Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        final Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 创建默认的httpClient实例.
            if (IGNORE_SSL) {
            	client = httpClient;
            } else {
            	client = (CloseableHttpClient) buildSslHttpClient(httpPost.getURI().toString());
            }
            // 执行请求
            response = client.execute(httpPost);

            // Examine the response status
            result.put("status", response.getStatusLine().getStatusCode());
            entity = response.getEntity();

            if (null != entity) {
                result.put("response", EntityUtils.toString(entity, Constants.UTF8));
                EntityUtils.consume(entity);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (httpPost != null) {
           	 	httpPost.releaseConnection();
           }
        }
        return result;
    }


    /**
     * 上传文件
     *
     * @param serverUrl
     *            服务器地址
     * @param localFilePath
     *            本地文件路径
     * @param serverFieldName
     * @param params
     * @return
     * @throws Exception
     */
    public String uploadFileImpl(final String httpUrl, final String localFilePath, final String serverFieldName, final Map<String, String> params)
            throws Exception {
        String respStr = null;
        HttpPost httpPost = null;
        HttpEntity reqEntity = null;
        HttpEntity resEntity = null;
        CloseableHttpResponse response = null;
        MultipartEntityBuilder multipartEntityBuilder = null;
        try {
        	httpPost = new HttpPost(httpUrl);

            // add the file params
            final File file = new File(localFilePath);
            multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addBinaryBody(serverFieldName, file, ContentType.MULTIPART_FORM_DATA, file.getName());
            // 设置上传的其他参数
            if (params != null && !params.isEmpty()) {
                for (final Entry<String, String> entry : params.entrySet()) {
                    multipartEntityBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
                }
            }
            reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);

			response = httpClient.execute(httpPost);
			logger.info(response.getStatusLine().toString());
			resEntity = response.getEntity();
			if (null != resEntity) {
				respStr = EntityUtils.toString(resEntity, Constants.UTF8);
				logger.info("Execute the request to complete：" + respStr);
				EntityUtils.consume(resEntity);
			}
        } finally {
        	 try {
                 // 关闭连接,释放资源
                 if (response != null) {
                     response.close();
                 }
             } catch (final IOException e) {
                 logger.error(e.getMessage(), e);
             }
             if (httpPost != null) {
            	 	httpPost.releaseConnection();
             }
        }
        return respStr;
    }

    public String sendHttpGet(final String httpUrl, final String deviceIp) {
        final HttpGet httpGet = new HttpGet(httpUrl); // 创建get请求
        logger.info(httpGet.toString());

        if (isHttps(httpUrl)) {
            return sendHttpsGet(httpGet, IGNORE_SSL, deviceIp);
        } else {
            return sendHttpGet(httpGet, deviceIp);
        }
    }

	/**
	 * 关闭系统时关闭httpClient
	 */
	public static void releaseHttpClient() {
		try {
			httpClient.close();
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (connManager != null) {
				connManager.shutdown();
			}
		}
	}
}
