package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;
import utils.WebDriverUtils;

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
    protected void tearDown(WebDriver driver, boolean success) {
        //do something before quiting driver

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement methodTrace = stackTrace[2];
        String testName = methodTrace.getClassName() + "." + methodTrace.getMethodName();
        log.info("Test name: " + testName);
        //log.info(Arrays.toString(stackTrace));

        if (PropertiesUtils.getTakeScreenshot()) { //check if we need screenshot
            if (!success) {
                ScreenshotUtils.takeScreenShot(driver, testName);
            }
        }

        quitDriver(driver);
    }


    protected void tearDown(WebDriver driver, ITestResult result) {
        tearDown(driver,result,0);
    }

    protected void tearDown(WebDriver driver, ITestResult result,int session) {
        String testName = result.getTestClass().getName();
        try {
            ifFailed(driver, result,session);
        } catch (AssertionError | Exception e) {

            log.error("Exception occurred in tearDown(" + testName + "). Message: " + e.getMessage());

        } finally {
            quitDriver(driver);
        }
    }


    private void ifFailed(WebDriver driver, ITestResult result,int session) {
        String testName = result.getTestClass().getName();
        session=Math.abs(session);
        if(session>0){
            testName=testName+"_"+session;
        }

        if (result.getStatus() == ITestResult.FAILURE) {
            if (PropertiesUtils.getTakeScreenshot() && !getListenerTakeScreenShot(result)) { //check if we need screenshot
                log.info("Taking ScreenShot from BaseTestClass");
                ScreenshotUtils.takeScreenShot(driver, testName);
            }
        }

    }

    private boolean getListenerTakeScreenShot(ITestResult result) {
        try {
            return (boolean) result.getTestContext().getAttribute("listenerTakeScreenShot");
        } catch (Exception e) {
            return false;
        }
    }

}
