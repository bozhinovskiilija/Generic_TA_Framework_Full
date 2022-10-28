package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public class PropertiesUtils extends LoggerUtils {

    private static final String commonPropertiesFilePath = "common.properties";
    private static final Properties properties = loadPropertiesFile();

    //generic method for reading all property files
    public static Properties loadPropertiesFile(String filePath) {

        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            Assert.fail("Can not load " + filePath + "file! Message: " + e.getMessage());
        }
        return properties;

    }


    private static Properties loadPropertiesFile() {
        return loadPropertiesFile(commonPropertiesFilePath);
    }


    private static String getProperty(String property) {

        log.trace("getProperty(" + property + ")");
        String result = properties.getProperty(property);
        Assert.assertNotNull(result,"Can not find property '" + property + "' in" + commonPropertiesFilePath + " file");
        return result;
    }

    public static String getEnvironemnt(){
        return getProperty("environment");
    }

    public static String getBrowser(){
        return getProperty("browser");
    }

    public static boolean getRemote(){
        String remote = getProperty("remote").toLowerCase();
        if(!(remote.equals("true")||remote.equals("false"))){
            Assert.fail("Can not convert remote property value '"+remote+" to boolean value");
        }
        return Boolean.parseBoolean(remote);
    }

    public static boolean getHeadless(){
        String headless = getProperty("headless").toLowerCase();
        if(!(headless.equals("true")||headless.equals("false"))){
            Assert.fail("Can not convert remote property value '"+headless+" to boolean value");
        }
        return Boolean.parseBoolean(headless);
    }

    public static String getHubUrl(){
        return getProperty("hubUrl");
    }

    public static String getDriverFolder(){
        return getProperty("driversFolder");
    }

    public static String getLocalBaseUrl(){
        return getProperty("localBaseUrl");
    }

    public static String getTestBaseUrl(){
        return getProperty("testBaseUrl");
    }

    public static String getProdBaseUrl(){
        return getProperty("prodBaseUrl");
    }

    public static String getBaseUrl(){
        String environment = getEnvironemnt().toLowerCase();
        String baseUrl=null;

        switch(environment) {
            case "local": {
                baseUrl = getLocalBaseUrl();
                break;
            }
            case "test": {
                baseUrl = getTestBaseUrl();
                break;
            }
            case "prod": {
                baseUrl = getProdBaseUrl();
                break;
            }
            default:{
                Assert.fail("Can not get BaseUrl! Environment '"+getEnvironemnt()+"' is not valid");
            }
        }
        return baseUrl;
    }

}
