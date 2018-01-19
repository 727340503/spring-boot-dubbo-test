package com.cherrypicks.tcc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PropertiesUtil {
    private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();
    private static Log LOGGER = LogFactory.getLog(PropertiesUtil.class);
    private static Properties properties = new Properties();
    
    static {
		loadFile(Constants.CMS_APPLICATION_PROPERTIES);
	}

    private static void loadFile(String filename) {
		try {
			properties.load(PropertiesUtil.class.getResourceAsStream("/" + filename));
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
    
    private PropertiesUtil() {
    }

//    public static String getProperties(final String path, final String key) throws IOException {
//
//        Properties properties = propertiesMap.get(path);
//
//        if (properties == null || properties.isEmpty()) {
//            final Resource resource = new ClassPathResource(path);
//            properties = PropertiesLoaderUtils.loadProperties(resource);
//
//            if (properties == null || properties.isEmpty()) {
//                throw new IOException(path + " not found");
//            } else {
//                propertiesMap.put(path, properties);
//            }
//        }
//
//        return properties.getProperty(key);
//    }

    public static String getProperties(final File file, final String key) throws IOException {
        Properties properties = propertiesMap.get(file.getPath());

        if (properties == null || properties.isEmpty()) {
            properties = new Properties();
            InputStream in = null;
            try {
                // final String path = FilenameUtils.getPath(file.getPath());
                // final String name = FilenameUtils.getName(file.getName());
                in = new BufferedInputStream(new FileInputStream(file));
                properties.load(in);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            propertiesMap.put(file.getPath(), properties);
        }

        return properties.getProperty(key);
    }
    
    public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
