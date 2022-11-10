package data;

import org.testng.Assert;
import utils.PropertiesUtils;

import java.util.Properties;

public class CommonString {

    private static final String localeFile = "locale_"+ PropertiesUtils.getLocale()+".loc";
    private static final String localePath = "\\locale\\"+localeFile;

    private static final Properties locale = PropertiesUtils.loadPropertiesFile(localePath);



    private static String getLocaleString(String title) {

        String result = locale.getProperty(title);
        Assert.assertNotNull(result,"String'" + title + "' does not exist in file " + localeFile + "!");
        return result;
    }


    //public static final String SUCCESSFUL_LOGOUT_MESSAGE = "You have been logged out.";
    //public static final String LOGIN_ERROR_MESSAGE = "Invalid username and password.";


    public static String getLogoutSuccessMessage(){

        return getLocaleString("SUCCESSFUL_LOGOUT_MESSAGE");
    }

    public static String getLoginErrorMessage(){

        return getLocaleString("LOGIN_ERROR_MESSAGE");
    }

}
