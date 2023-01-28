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


    public static String getLogoutSuccessMessage(){

        return getLocaleString("SUCCESSFUL_LOGOUT_MESSAGE");
    }

    public static String getLoginErrorMessage(){

        return getLocaleString("LOGIN_ERROR_MESSAGE");
    }

    public static String getRegisterSuccessMessage() {
        return getLocaleString("REGISTER_SUCCESS_MESSAGE");
    }


    // public static final String SUCCESSFUL_LOGOUT_MESSAGE = "You have been logged out.";
    // public static final String LOGIN_ERROR_MESSAGE = "Invalid username and password.";

    public static String getDeleteHeroMessage(String heroName, String heroClass, Integer heroLevel){
        return getLocaleString("DELETE_HERO_MESSAGE").replace("%HERO_NAME%",heroName).replace("%HERO_CLASS%",heroClass).replace("%HERO_LEVEL%",String.valueOf(heroLevel));
    }

    public static final Integer INTERNAL_SERVER_ERROR_CODE = 500;
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String SERVER_ERROR_FORBIDDEN = "Forbidden";
    public static final String EXPECTED_EXCEPTION = "java.lang.IllegalArgumentException";
    public static final String EXPECTED_MESSAGE_NON_EXISTING_USER = "User " + "%s" + " does not exists!";
    public static final String EXPECTED_MESSAGE_ALREADY_EXISTING_USER = "User " + "%s" + " already exists!";
    public static final String EXPECTED_MESSAGE_EMAIL_NOT_SPECIFIED = "Email is not specified!";
    public static final String EXPECTED_MESSAGE_ACCESS_DENIED = "Access is denied";

    public static final String EXPECTED_PATH = "User " + "%s" + " does not exists!";

    public static final String TOOLTIP_TEXT = "This is one useless tooltip! Nevertheless, try to get its content!";
    public static final String DRAG_AND_DROP_TEXT = "You have successfully performed drag&drop action!";

}
