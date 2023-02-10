package listeners;

import annotations.Jira;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportUtils;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class TestListener extends LoggerUtils implements ITestListener {

    WebDriver driver = null;

    private static final boolean listenerTakeScreenShot = PropertiesUtils.getTakeScreenshot();
    private static boolean updateJira = false;
    private static String extendReportFolder;
    private static String extentReportName;
    private static String extentReportFilesFolderName;
    private static String extentReportFilesFolder;
    private static String extentReportHtmlFilePath;

    //different instance of test in different thread
    private static final ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();

    private static ExtentReports extentReport = null;

    @Override
    public void onStart(final ITestContext context) {

        String sSuiteName = context.getSuite().getName();
        log.info("[SUITE STARTED] " + sSuiteName);
        updateJira = getUpdateJira(context);
        context.setAttribute("listenerTakeScreenShot", listenerTakeScreenShot);

        extendReportFolder = ExtentReportUtils.getExtentReportFolder(sSuiteName);
        extentReportName = ExtentReportUtils.getExtentReportName(sSuiteName);
        extentReportFilesFolderName = ExtentReportUtils.getExtentReportFilesFolderName(sSuiteName);
        extentReportFilesFolder = ExtentReportUtils.getExtentReportFilesFolder(sSuiteName);
        extentReportHtmlFilePath = ExtentReportUtils.getExtentReportPathHtmlFilePath(sSuiteName);

        try {
            FileUtils.cleanDirectory(new File(extendReportFolder));
        } catch (Exception e) {
            log.warn("Extent Report Folder " + extendReportFolder+ "can not be cleaned" + e.getMessage());
        }

        extentReport = ExtentReportUtils.createExtentReportInstance(sSuiteName);
    }


    @Override
    public void onFinish(final ITestContext context) {

        String sSuiteName = context.getSuite().getName();
        log.info("[SUITE FINISHED] " + sSuiteName);
        if(extentReport!=null){
            extentReport.flush();

        }
    }

    @Override
    public void onTestStart(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST STARTED] " + sTestName);

        //information related to one test
        //problem when executing tests in parallel - make it thread safe(thread local)
        ExtentTest test = extentReport.createTest(sTestName);

        Jira jira = getJiraDetails(result);
        if(jira != null){
            test.info("JiraID" + jira.jiraID());
            test.assignAuthor(jira.owner());
        }

        // String packageName = result.getTestClass().getRealClass().getPackage().getName();
        // test.assignCategory(packageName);

        //sanity, regression..
        String[] groups = result.getMethod().getGroups();
        for(String group : groups){
            test.assignCategory(group);
        }

        extentTestThread.set(test); //each extent test instance in different thread

    }


    @Override
    public void onTestSuccess(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST SUCCESS] " + sTestName);
        if(updateJira){
            Jira jira = getJiraDetails(result);
            if(jira==null){
                log.warn("Listener can not get jira details for test '" + sTestName + "' !");
            }else {
                String jiraID = jira.jiraID();
                String owner = jira.owner();
                log.info("JiraID: " + jiraID);
                log.info("Owner: " + owner);
                //PropertiesUtils.get
                //String sJiraID = getJiraID(result);
                // Create PASSED result on Jira
            }

            String successText = "<b>Test " + sTestName + " Passed!</b>";
            Markup markup = MarkupHelper.createLabel(successText, ExtentColor.GREEN);
            extentTestThread.get().log(Status.PASS,markup);
        }
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
        Jira jira = getJiraDetails(result);
        String errorMessage = result.getThrowable().getMessage();
        String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());

        //Add ScreenShots
        //Add Error message
        //Add stacktrace

        String skippedTestText = "<b>Test " + testName + " Failed!</b>";
        Markup markup = MarkupHelper.createLabel(skippedTestText, ExtentColor.RED);
        extentTestThread.get().log(Status.FAIL,markup);

    }


    @Override
    public void onTestSkipped(final ITestResult result) {

        String sTestName = result.getTestClass().getName();
        log.info("[TEST SKIPPED] " + sTestName);
        // delete screenshot from temp folder (if it was saved in temp folder)

        String skippedTestText = "<b>Test " + sTestName + " Skipped!</b>";
        Markup markup = MarkupHelper.createLabel(skippedTestText, ExtentColor.ORANGE);
        extentTestThread.get().log(Status.SKIP,markup);
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

    private static Jira getJiraDetails(ITestResult result){

        String sTestName = result.getTestClass().getName();
        return result.getTestClass().getRealClass().getAnnotation(Jira.class);
    }

    private static boolean getUpdateJira(ITestContext context){
        String suiteName = context.getSuite().getName();
        String updateJira = context.getSuite().getParameter("updateJira");
        if(updateJira==null){
            log.warn("Parameter updateJira is not set in '" + suiteName + "' suite");
            return false;
        }else{
            updateJira = updateJira.toLowerCase();
            if(!(updateJira.equals("true")||updateJira.equals("false"))){
                log.warn("Parameter 'updateJira' in '"+suiteName+"' is not recognized as boolean value!");
                return false;
            }
        }
        boolean bUpdateJira = Boolean.parseBoolean(updateJira);
        log.info("Update Jira" + bUpdateJira);
        return bUpdateJira;
    }

}
