package starter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import starter.utils.data.EnvironmentPropertyMap;

public class Configuration{
    
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    private static Properties configuration;

    public static String get(String property) { return configuration.getProperty(property); }

    static {
        initConfig();
    }

    public static void initConfig(){
        Properties localProperties = new Properties();
        String propertyFile = "";

        try {
            if (System.getProperty("environment").equalsIgnoreCase(EnvironmentPropertyMap.ENV_SIT.getEnvironment())){
                propertyFile = EnvironmentPropertyMap.ENV_SIT.getPropertyFile();
                logger.info("Using SIT Properties");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if(propertyFile.equals("")){
                logger.info("Using Local Properties");
                propertyFile = EnvironmentPropertyMap.ENV_LOCAL.getPropertyFile();
            }
        }

        propertyFile = "properties/" + propertyFile + ".properties";

        try {
            InputStream propertyFileInputStream = getClassPathResourceStream(propertyFile);
            loadProperties(localProperties, getClassPathResourceStream(propertyFile));
            propertyFileInputStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Could not load properties", e);
        } finally {
            configuration = localProperties;
        }
    }

    public static void loadProperties(Properties props, InputStream inputStream){
        try {
            props.load(inputStream);
        } catch (IOException ioexception) {
            logger.error(ioexception.getMessage());
        }
    }

    public static InputStream getClassPathResourceStream(String classPathResourceLoc){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(classPathResourceLoc);
    }
    
}
