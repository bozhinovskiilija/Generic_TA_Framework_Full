package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;
import utils.WebDriverUtils;

import java.util.Arrays;

public class BaseTestClass extends LoggerUtils {

    WebDriver driver;

    //wrappers for setting up WebDriver, tear down test, etc
    protected WebDriver setUpDriver() {

        return driver = WebDriverUtils.setupDriver();
    }

    protected void quitDriver(WebDriver driver) {

        WebDriverUtils.QuitDriver(driver);
    }

    //tear down
    protected void tearDown(WebDriver driver,boolean success){
        //do something before quiting driver

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement methodTrace=stackTrace[2];
        String testName = methodTrace.getClassName() + "." + methodTrace.getMethodName();
        log.info("Test name: "+testName);
        //log.info(Arrays.toString(stackTrace));

        if(PropertiesUtils.getTakeScreenshot()){ //check if we need screenshot
            if(!success){
                ScreenshotUtils.takeScreenShot(driver,testName);
            }
        }

        quitDriver(driver);
    }

    protected void tearDown(WebDriver driver, ITestResult result){
        String testName=result.getName();
        if(PropertiesUtils.getTakeScreenshot()){ //check if we need screenshot
            if(result.getStatus() == ITestResult.FAILURE){
                ScreenshotUtils.takeScreenShot(driver,testName);
            }
        }

        quitDriver(driver);
    }


}
