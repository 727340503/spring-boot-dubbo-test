package com.cherrypicks.tcc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/** 
 * @author Sea 
 * @date 2017-6-1
 */
public class AWSClient {
	
	private static final Logger logger = LoggerFactory.getLogger(AWSClient.class);
	 
	private static AmazonS3 s3  = null;
	private static TransferManager tx  = null;

	public AWSClient(String accessKey,String secretKey){
		init_with_key(accessKey,secretKey);
	}

	//通过在代码中包含 access key id 和 secret access key 连接 aws；
	private void init_with_key(String accessKey,String secretKey){
		AWSCredentials credentials = null;
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		s3 = new AmazonS3Client(credentials);
		Region apSouthEast_1 = Region.getRegion(Regions.AP_SOUTHEAST_1);
		s3.setRegion(apSouthEast_1);
		tx = new TransferManager(s3);
	}

	//用于创建一个名为bucketName的s3 bucket；
	public void createBucket(String bucketName) {
		if (s3.doesBucketExist(bucketName) == true) {
			logger.info(bucketName + " already exist!");
			return;
		}
		logger.info("creating " + bucketName + " ...");
		s3.createBucket(bucketName);
		logger.info(bucketName + " has been created!");
	}

	/**
	 * 获取该容器下面的所有信息（文件目录集合和文件信息集合）
	 * 
	 * @param bucketName
	 * @return
	 */
	public ObjectListing getBacketObjects(String bucketName) {
		if (bucketName.isEmpty()) {
			return null;
		}
		return s3.listObjects(bucketName);
	}

	/**
	 * 获取某个文件（前缀路径）下的所有信息
	 * 
	 * @param bucketName
	 * @param prefix
	 * @param isDelimiter
	 * @return
	 */
	public ObjectListing getBacketObjects(String bucketName, String prefix, Boolean isDelimiter) {
		if (bucketName == null || bucketName.isEmpty()) {
			return null;
		}
		ListObjectsRequest objectsRequest = new ListObjectsRequest().withBucketName(bucketName);
		if (prefix != null && !prefix.isEmpty()) {
			objectsRequest = objectsRequest.withPrefix(prefix);
		}
		if (isDelimiter) {
			objectsRequest = objectsRequest.withDelimiter("/");
		}
		return s3.listObjects(objectsRequest);
	}

	
	public boolean checkObjectLastModified(String bucketName, String key, File compareFile) {
		logger.info("Listing objects of " + bucketName);
		ObjectListing objectListing = s3.listObjects(bucketName);
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			if (key.equals(objectSummary.getKey())
					&& objectSummary.getLastModified().after(new Date(compareFile.lastModified()))) {
				logger.info(objectSummary.getKey() + " LastModified Time:" +  DateUtil.parseDate2(objectSummary.getLastModified(), DateUtil.DATETIME_PATTERN_DEFAULT));
				logger.info(compareFile.getAbsolutePath() + " LastModified Time:" + DateUtil.parseDate2(new Date(compareFile.lastModified()), DateUtil.DATETIME_PATTERN_DEFAULT));
				return true;
			}
		}
		return false;
	}

	//判断名为bucketName的bucket里面是否有一个名为key的object；
	public boolean isObjectExist(String bucketName, String key) {
		ObjectListing objectListing = s3.listObjects(bucketName);
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			if (key.equals(objectSummary.getKey())){
				return true;
			}
		}
		return false;
	}

	//输出"s3://"+bucketname+"/"key对应的object对应的信息；
	public String showContentOfAnObject(String bucketName, String key, String encoding) {
		S3Object object = null;
		BufferedReader reader = null;
		String result = null;
		try {
			object = s3.getObject(new GetObjectRequest(bucketName, key));
			reader = new BufferedReader(new InputStreamReader(object.getObjectContent(),encoding));
			result = IOUtils.toString(reader);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(reader);
        }
		return result;
	}

	//输出"s3://"+bucketname+"/"key对应的object对应的信息(这个object对应的文件时gzip格式的)；
	public void showContentOfAnGzipObject(String bucketName, String key) {
		BufferedReader reader = null;
		S3Object object = null;
		try {
			object = s3.getObject(new GetObjectRequest(bucketName, key));
			reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(object.getObjectContent())));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				logger.info("    " + line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
            try {
                // 关闭连接,释放资源
                if (reader != null) {
                	reader.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
	}
	
	// 列出我的s3上所有的bucket的名字；
	public List<String> listBuckets() {
		List<String> bucketList = new ArrayList<String>();
		for (Bucket bucket : s3.listBuckets()) {
			bucketList.add(bucket.getName());
		}
		return bucketList;
	}

	//删除一个名为bucketName的bucket；
	public void deleteBucket(String bucketName) {
		if (s3.doesBucketExist(bucketName) == false) {
			logger.info(bucketName + " does not exists!");
			return;
		}
		logger.info("deleting " + bucketName + " ...");
		ObjectListing objectListing = s3.listObjects(bucketName);
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			String key = objectSummary.getKey();
			s3.deleteObject(bucketName, key);
		}
		s3.deleteBucket(bucketName);
		logger.info(bucketName + " has been deleted!");
	}

	//删除名为bucketName的bucket里面所有key的前缀和prefix匹配的object；
	public void deleteObjectsWithPrefix(String bucketName, String prefix) {
		if (s3.doesBucketExist(bucketName) == false) {
			logger.info(bucketName + " does not exists!");
			return;
		}
		logger.info("deleting " + prefix + "* in " + bucketName + " ...");
		int pre_len = prefix.length();
		ObjectListing objectListing = s3.listObjects(bucketName);
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			String key = objectSummary.getKey();
			int len = key.length();
			if (len < pre_len)
				continue;
			int i;
			for (i = 0; i < pre_len; i++)
				if (key.charAt(i) != prefix.charAt(i))
					break;
			if (i < pre_len)
				continue;
			s3.deleteObject(bucketName, key);
		}
		logger.info("All " + prefix + "* deleted!");
	}
	
	//上传一个本地文件(对应位置为path)上传到名为bucketName的bucket；
	public void uploadFileToBucket(String path, String bucketName) {
		File fileToUpload = new File(path);
		if (fileToUpload.exists() == false) {
			logger.info(path + " not exists!");
			return;
		}
		PutObjectRequest request = new PutObjectRequest(bucketName, fileToUpload.getName(), fileToUpload);
		Upload upload = tx.upload(request);
		while ((int) upload.getProgress().getPercentTransferred() < 100) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info(path + " upload succeed!");
	}
	
	/**
	 * 
	 * 上传 文件对象到容器
	 * 
	 * @param bucketName
	 * @param StorageObjectVoPath
	 * @param fileName
	 * @param uploadFile
	 * @return
	 */
	public PutObjectResult creatObject(String bucketName, String path, String fileName,
			File uploadFile) {
		if (StringUtils.isBlank(path) || "null".equals(path)) {
			path = "";
		}
		if (uploadFile == null) {
			return null;
		}
		String fileAllPath = path + fileName;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(uploadFile);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		PutObjectResult result = s3.putObject(bucketName, fileAllPath, inputStream, new ObjectMetadata());
		return result;
	}


	// 创建文件目录
	public Boolean creatpath(String bucketName, String path, String folderName) {
		if (bucketName == null || folderName == null) {
			return false;
		}
		if (path == null || path.isEmpty() || "null".equals(path)) {
			path = "";
		}
		String key = path + folderName + "/";
		ByteArrayInputStream local = new ByteArrayInputStream("".getBytes());
		s3.putObject(bucketName, key, local, new ObjectMetadata());
		return true;
	}
	
	public ObjectMetadata download(String bucketName, String objectName, File destinationFile) {
		if (bucketName.isEmpty() || objectName.isEmpty()) {
			return null;
		}
		return s3.getObject(new GetObjectRequest(bucketName, objectName), destinationFile);
	}

	public S3Object download(String bucketName, String objectName) {
		if (bucketName.isEmpty() || objectName.isEmpty()) {
			return null;
		}
		return s3.getObject(bucketName, objectName);
	}
	
	/**
	 * 生成文件url
	 * 
	 * @param bucketName
	 * @param objectName
	 * @return
	 */
	public String getDownloadUrl(String bucketName, String objectName) {
		if (bucketName.isEmpty() || objectName.isEmpty()) {
			return null;
		}
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
		return s3.generatePresignedUrl(request).toString();
	}


	public Boolean deleteObject(String bucketName, String objectName) {
		if (bucketName.isEmpty() || objectName.isEmpty()) {
			return false;
		}
		s3.deleteObject(bucketName, objectName);
		return true;
	}

	/**
	 * 获取当前容器下面的目录集合
	 * 
	 * @param objects
	 * @return
	 */
	public List<String> getDirectList(ObjectListing objects) {
		List<String> diectList = new ArrayList<String>();
		String prefix = objects.getPrefix();
		do {
			List<String> commomprefix = objects.getCommonPrefixes();

			for (String comp : commomprefix) {
				String dirName = comp.substring(prefix == null ? 0 : prefix.length(), comp.length() - 1);
				diectList.add(dirName);
			}
			objects = s3.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
		return diectList;
	}
	
	/**
	 * 获取当前容器下面的文件集合
	 * 
	 * @param objects
	 * @return
	 */
	public List<String> getFileList(ObjectListing objects) {
		List<String> fileList = new ArrayList<String>();
		String prefix = objects.getPrefix();
		do {
			for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
				if (prefix != null && objectSummary.getKey().equals(prefix.trim())) {
					continue;
				}
				String fileName = objectSummary.getKey().substring(prefix == null ? 0 : prefix.length(),
						objectSummary.getKey().length());
				fileList.add(fileName);
			}
			objects = s3.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
		return fileList;
	}

	public List<String> getObjectList(String bucketName, String prefix) {
		if (StringUtils.isBlank(bucketName)) {
			return null;
		}
		ListObjectsRequest objectsRequest = new ListObjectsRequest().withBucketName(bucketName);
		if (prefix != null && !prefix.isEmpty()) {
			objectsRequest = objectsRequest.withPrefix(prefix);
		}
		objectsRequest = objectsRequest.withDelimiter("/");
		ObjectListing objects = s3.listObjects(objectsRequest);
		List<String> resultList = new ArrayList<String>();
		List<String> dirList = getDirectList(objects);
		if (dirList != null && dirList.size() > 0) {
			resultList.addAll(dirList);
		}
		List<String> fileList = getFileList(objects);
		if (fileList != null && fileList.size() > 0) {
			resultList.addAll(fileList);
		}
		return resultList;
	} 

	/**
	 * 移动对象信息到目标容器
	 * 
	 * @param OrgbucketName
	 * @param orgKey
	 * @param destinationName
	 * @param destinationKey
	 * @return
	 */
	public Boolean moveObject(String OrgbucketName, String orgKey, String destinationName, String destinationKey) {
		CopyObjectResult result = s3.copyObject(OrgbucketName, orgKey, destinationName, destinationKey);
		Boolean isDelete = deleteObject(OrgbucketName, orgKey);
		if (result != null) {
			return isDelete;
		}
		return false;
	}
	  
	/**
	 * 移动目标文件夹信息到目标容器
	 * 
	 * @param objects
	 * @param destinationBucket
	 * @return
	 */
	public Boolean moveForder(ObjectListing objects, String destinationBucket) {
		String bucketName = objects.getBucketName();
		do {
			for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
				s3.copyObject(bucketName, objectSummary.getKey(), destinationBucket,
						objectSummary.getKey());
				deleteObject(bucketName, objectSummary.getKey());
			}
			objects = s3.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
		return true;
	} 

	/**
	 * 删除文件夹内容（必须先遍历删除文件夹内的内容）
	 * 
	 * @param objects
	 * @return
	 */
	public Boolean deleteForder(ObjectListing objects) {
		String bucketName = objects.getBucketName();
		do {
			for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
				deleteObject(bucketName, objectSummary.getKey());
			}
			objects = s3.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
		return true;
	}

	public static void main(String[] args) throws Exception {
		String accessKey = "AKIAIYSEUASSCH7SV7RQ";
		String secretKey = "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";
		String bucketName = "starbeacon-demo";
		AWSClient client = new AWSClient(accessKey,secretKey);
		List<String> fileNames = client.getFileList(client.getBacketObjects(bucketName,"store/",false));
		for(String fileName : fileNames) {
			System.out.println(fileName);
		}
		/*List<String> list = client.getDirectList(client.getBacketObjects(bucketName));
		for(String str :list){
			System.out.println(str+"/n");
		}*/
		//client.download(bucketName, "json/list_maps",new File("D://test"));
		//String content = client.showContentOfAnObject(bucketName,"json/list_maps",Constants.UTF8);
		//FileUtils.writeStringToFile(new File("D:\\abc.json"), content);
		//System.out.println(content);	
		//System.out.println(client.checkObjectLastModified(bucketName,
			//	"json/list_maps", new File("E:\\data\\upload\\starBeacon_icon\\c53e7c22c2a34a1e87a840356565323b.png")));
	
	}
}
