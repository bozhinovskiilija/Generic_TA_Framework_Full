package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.charset.StandardCharsets;

public class ExtentReportUtils extends LoggerUtils{

    private static final String extentReportBaseFolder = System.getProperty("user.dir") + PropertiesUtils.getReportsFolder();

    //folder with suite name under base report folder
    public static String getExtentReportFolder(String suiteName){
        return extentReportBaseFolder + suiteName.replace(" ","_").toLowerCase()+"\\";
    }

    public static String getExtentReportName(String suiteName){
        return suiteName.replace(" ", "_").toLowerCase();
    }

    //contains screenshots and other files need for the report
    public static String getExtentReportFilesFolderName(String suiteName){
        return getExtentReportName(suiteName) + "_files";
    }

    //whole path to files folder
    public static String getExtentReportFilesFolder(String suiteName){
        return getExtentReportFolder(suiteName) + getExtentReportFilesFolderName(suiteName)+ "\\";
    }

    //path to the extentReport html file
    public static String getExtentReportPathHtmlFilePath(String suiteName){
        return getExtentReportFolder(suiteName) + getExtentReportName(suiteName) + ".html";
    }

    public static ExtentReports createExtentReportInstance(String suiteName){
        String extentReportPath = getExtentReportPathHtmlFilePath(suiteName);

        ExtentSparkReporter extentReporter = new ExtentSparkReporter(extentReportPath);
        extentReporter.config().setEncoding(StandardCharsets.UTF_8.toString());
        extentReporter.config().setReportName(suiteName + " Report");
        extentReporter.config().setDocumentTitle(suiteName + " Results");
        extentReporter.config().setTheme(Theme.STANDARD);

        ExtentReports extentReport = new ExtentReports();
        extentReport.setSystemInfo("Environment", PropertiesUtils.getBaseUrl());
        extentReport.setSystemInfo("Browser", PropertiesUtils.getBrowser());
        extentReport.setSystemInfo("Headless", String.valueOf(PropertiesUtils.getHeadless()));
        extentReport.setSystemInfo("Remote", String.valueOf(PropertiesUtils.getRemote()));

        extentReport.attachReporter(extentReporter);

        return extentReport;
    }



}
