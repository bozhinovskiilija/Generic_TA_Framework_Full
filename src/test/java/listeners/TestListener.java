package listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.UUID;

public class TestListener extends LoggerUtils implements ITestListener {

    WebDriver driver = null;

    private static final boolean listenerTakeScreenShot = PropertiesUtils.getTakeScreenshot();


    @Override
    public void onTestStart(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST STARTED] " + sTestName);
    }


    @Override
    public void onTestSuccess(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST SUCCESS] " + sTestName);
        //String sJiraID = getJiraID(result);
        // Create PASSED result on Jira
    }


    @Override
    public void onTestFailure(final ITestResult result) {
        //ITestListener.super.onTestFailure(result);

        /* MY Implementation

        ITestContext context = result.getTestContext();
        driver = (WebDriver) context.getAttribute("WebDriver");
        Allure.addAttachment(UUID.randomUUID().toString(), new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(
            OutputType.BYTES)));*/

        String testName = result.getTestClass().getName();
        log.info("[TEST FAILED] " + testName);
        if (listenerTakeScreenShot) {
            WebDriver[] drivers = getWebDriverInstances(result);
            log.info("Taking ScreenShot from Listener!");
            if (drivers != null) {
                for (int i = 0; i < drivers.length; i++) {
                    String screenShotName = testName;
                    if (drivers.length > 1) {
                        screenShotName = screenShotName + "_" + (i + 1);
                    }
                    String pathToScreenShot = ScreenshotUtils.takeScreenShot(drivers[i], screenShotName);
                    //Allure.addAttachment(UUID.randomUUID().toString(),pathToScreenShot);

                }
            }
        }
    }


    @Override
    public void onTestSkipped(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST SKIPPED] " + sTestName);
        // delete screenshot from temp folder (if it was saved in temp folder)

    }


    @Override
    public void onStart(final ITestContext context) {

        String sSuiteName = context.getSuite().getName();
        log.info("[SUITE STARTED] " + sSuiteName);
        context.setAttribute("listenerTakeScreenShot", listenerTakeScreenShot);
    }


    @Override
    public void onFinish(final ITestContext context) {

        String sSuiteName = context.getSuite().getName();
        log.info("[SUITE FINISHED] " + sSuiteName);
    }


    private static WebDriver getWebDriverInstance(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        WebDriver driver = null;
        try {
            //driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
            driver = (WebDriver) result.getTestContext().getAttribute(sTestName + ".driver");
        } catch (Exception e) {
            log.warn("Cannot get Driver Instance for test '" + sTestName + "'! Message: " + e.getMessage());
        }
        return driver;
    }


    private static WebDriver[] getWebDriverInstances(ITestResult result) {
        String testName = result.getTestClass().getName();
        WebDriver[] drivers = null;
        try {
            //driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
            drivers = (WebDriver[]) result.getTestContext().getAttribute(testName + ".drivers");
        } catch (Exception e) {
            log.warn("Cannot get Driver Instance(s) for test '" + testName + "'! Message: " + e.getMessage());
        }
        return drivers;
    }


    private static String getJiraID(ITestResult result) {
        String sTestName = result.getTestClass().getName();
        String jiraID = null;
        try {
            jiraID = (String) result.getTestClass().getRealClass().getDeclaredField("sJiraID")
                .get(result.getInstance());
            log.info("JiraID: " + jiraID);
            jiraID = (String) result.getTestContext().getAttribute(sTestName + ".jiraID");
            log.info("JiraID: " + jiraID);
            jiraID = result.getTestName();
            log.info("JiraID: " + jiraID);
            jiraID = result.getMethod().getDescription();
            log.info("JiraID: " + jiraID);
            jiraID = Arrays.toString(result.getMethod().getGroups());
            log.info("JiraID: " + jiraID);
        } catch (Exception e) {
            log.warn("Cannot get JiraID for test '" + sTestName + "'! Message: " + e.getMessage());
        }
        return jiraID;
    }

}
