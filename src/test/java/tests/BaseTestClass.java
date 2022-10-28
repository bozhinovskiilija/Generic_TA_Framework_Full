package tests;

import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;
import utils.WebDriverUtils;

public class BaseTestClass extends LoggerUtils {

    //wrappers for setting up WebDriver, tear down test, etc

    protected WebDriver setUpDriver(){
        return WebDriverUtils.setupDriver();
    }

    protected void quitDriver(WebDriver driver){
        WebDriverUtils.QuitDriver(driver);
    }
}
