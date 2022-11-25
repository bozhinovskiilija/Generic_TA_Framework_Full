package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtils extends LoggerUtils {

    //root folder of the framework
    private static final String screenshotsFolder = System.getProperty(
        "user.dir") + PropertiesUtils.getScreenshotsFolder();

    private static String createScreenShotPath(String testName) {
        return screenshotsFolder + testName + ".png";
    }


    public static String takeScreenShot(WebDriver driver, String testName) {
        log.trace("takeScreenShot(" + testName + ")");
        if (WebDriverUtils.hasDriverQuit(driver)) {
            log.warn("Screenshot for test '" + testName + "' could not be taken!. Driver instance has quit!");
            return null;
        }

       // String sessionID=WebDriverUtils.getSessionID(driver).toString();
        //String screenshotName=testName + "_" + sessionID;
        String pathToFile = createScreenShotPath(testName);

        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File srcFile=screenshot.getScreenshotAs(OutputType.FILE);
        File dstFile=new File(pathToFile);
        try {
            FileUtils.copyFile(srcFile,dstFile);
            log.info("Screenshot for test '"+testName+"' is saved in file: "+ pathToFile);
        } catch (IOException e) {
            log.warn("Screenshot for test '"+testName+"' could not be saved in file '"+pathToFile+"'. Message: "+e.getMessage());
            return null;
        }

        return pathToFile;
    }

}
