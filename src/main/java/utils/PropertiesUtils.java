package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils extends LoggerUtils {

    private static final String commonPropertiesFilePath = "application.properties";
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

    public static String getAdminUsername(){
        return getProperty("adminUsername");
    }

    public static String getAdminPassword(){
        return getProperty("adminPasswod");
    }

    public static String getLocale(){
        return getProperty("locale");
    }

    public static boolean getTakeScreenshot(){
        String takeScreenShots = getProperty("takeScreenshots").toLowerCase();
        if(!(takeScreenShots.equals("true")||takeScreenShots.equals("false"))){
            Assert.fail("Can not convert remote property value '"+takeScreenShots+" to boolean value");
        }
        return Boolean.parseBoolean(takeScreenShots);
    }

    public static String getScreenshotsFolder(){

        return getProperty("screenshotsFolder");
    }

    public static String getEndUserUsername(){
        return getProperty("endUserUsername");
    }

    public static String getEndUserPassword(){
        return getProperty("endUserPassword");
    }


    public static String getDefaultPassword(){
        return getProperty("defaultPassword");
    }

    public static String getLocalDatasourceUrl(){
        return getProperty("localDatasourceUrl");
    }

    public static String getTestDatasourceUrl(){
        return getProperty("testDatasourceUrl");
    }

    public static String getProdDatasourceUrl(){
        return getProperty("prodDatasourceUrl");
    }

    public static String getDataSourceUrl(){

        String environment = getEnvironemnt().toLowerCase();

        String dataSourceUrl=null;

        switch(environment) {
            case "local": {
                dataSourceUrl = getLocalDatasourceUrl();
                break;
            }
            case "test": {
                dataSourceUrl = getTestDatasourceUrl();
                break;
            }
            case "prod": {
                dataSourceUrl = getProdDatasourceUrl();
                break;
            }
            default:{
                Assert.fail("Can not get DataSourceBaseUrl! Environment '"+getEnvironemnt()+"' is not valid");
            }
        }
        return dataSourceUrl;
    }

    //database username & password
    public static String getRootUsername(){
        return getProperty("rootUsername");
    }

    public static String getRootPassword(){
        return getProperty("rootPassword");
    }
}
