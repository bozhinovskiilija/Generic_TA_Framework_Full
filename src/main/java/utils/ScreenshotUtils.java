package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils extends LoggerUtils {

    //root folder of the framework
    private static final String screenshotsFolder = System.getProperty(
        "user.dir") + PropertiesUtils.getScreenshotsFolder();

    private static final String imageFolder = System.getProperty("user.dir") + PropertiesUtils.getImagesFolder();


    private static String createWebElementSnaphotPat(String elementName) {
        return screenshotsFolder + elementName + ".png";
    }


    private static String createScreenShotPath(String testName) {
        return screenshotsFolder + testName + "_" + DateTimeUtils.getDateTimeStamp() + ".png";
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
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File dstFile = new File(pathToFile);
        try {
            FileUtils.copyFile(srcFile, dstFile);
            //  Allure.addAttachment("Screenshot", FileUtils.openInputStream(srcFile));
            log.info("Screenshot for test '" + testName + "' is saved in file: " + pathToFile);
        } catch (IOException e) {
            log.warn(
                "Screenshot for test '" + testName + "' could not be saved in file '" + pathToFile + "'. Message: " + e.getMessage());
            return null;
        }

        return pathToFile;
    }


    public static BufferedImage takeScreenShot(WebDriver driver) {
        log.trace("takeScreenShot()");
        if (WebDriverUtils.hasDriverQuit(driver)) {
            Assert.fail("Screenshot for test could not be taken! Driver instance has quit!");
        }
        BufferedImage fullScreen = null;

        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        try {
            fullScreen = ImageIO.read(screenshot.getScreenshotAs(OutputType.FILE));
        } catch (IOException e) {
            Assert.fail("Screenshoot could not be taken: " + e.getMessage());
        }

        return fullScreen;
    }


    //external dependency
    public static Screenshot takeScreenShotWithAshot(WebDriver driver) {
        log.trace("takeScreenShotWithAshot");
        AShot shot = new AShot();
        return shot.takeScreenshot(driver);
    }


    public static BufferedImage takeSnapshotOfWebElement(WebDriver driver, WebElement element) {
        log.trace("takeScreenShot(" + element + ")");

        if (WebDriverUtils.hasDriverQuit(driver)) {
            Assert.fail("Snapshot of web element" + element + "could not be taken! Driver instance has quit!");
        }
        BufferedImage fullScreen = takeScreenShot(driver);
        //Point is part of selenium
        Point elementLocation = element.getLocation();
        Dimension elementDimension = element.getSize();//part of selenium

        return fullScreen.getSubimage(elementLocation.getX(), elementLocation.getY(),
            elementDimension.getWidth(), elementDimension.getHeight());

    }


    public static Screenshot takeSnapshotOfWebElementAshot(WebDriver driver, WebElement element) {
        log.trace("takeScreenShot(" + element + ")");
        AShot shot = new AShot();
        return shot.takeScreenshot(driver, element);
    }


    private static void saveBufferedImage(BufferedImage bufferedImage, String pathToFile) {
        log.trace("saveBufferedImage(" + pathToFile + ")");
        File file = new File(pathToFile);
        try {
            ImageIO.write(bufferedImage, "PNG", file);
        } catch (IOException e) {
            log.warn("Image could not be saved in file '" + file + ", Message: " + e.getMessage());
        }
    }


    public static void saveSnapshotOfWebElement(WebDriver driver, WebElement element, String fileName) {
        log.trace("saveSnapshotOfWebElement(" + element + ", " + fileName + ")");
        String pathToFile = createScreenShotPath(fileName);
        BufferedImage bufferedImage = takeSnapshotOfWebElement(driver, element);
        saveBufferedImage(bufferedImage, pathToFile);
        log.info("Snapshot of WebElement is saved in: " + pathToFile);
    }


    public static void saveSnapshotOfWebElementWithAshot(WebDriver driver, WebElement element, String fileName) {
        log.trace("saveSnapshotOfWebElementWithAshot(" + element + ", " + fileName + ")");
        String pathToFile = createScreenShotPath(fileName);
        Screenshot screenshot = takeSnapshotOfWebElementAshot(driver, element);
        BufferedImage bufferedImage = screenshot.getImage();
        saveBufferedImage(bufferedImage, pathToFile);
        log.info("Snapshot of WebElement is saved: " + pathToFile);
    }


    //read the image from images folder in test directory
    private static BufferedImage loadBufferedImage(String fileName) {
        String imagePath = imageFolder + fileName;
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            Assert.fail("Can not load image form file '" + imagePath + "'. Message: " + e.getMessage());
        }
        return bufferedImage;
    }


    public static boolean compareSnapshotWithImage(BufferedImage actualImage, String fileName) {
        log.trace("compareSnapshotWithImage(" + fileName + ")");
        BufferedImage expectedImage = loadBufferedImage(fileName);
        return compareImages(actualImage, expectedImage);
    }


    //compare pixel by pixel - NOT recommended
    private static boolean compareImages(BufferedImage imageA, BufferedImage imageB) {
        if (imageA.getWidth() != imageB.getWidth() || imageA.getHeight() != imageB.getHeight()) {
            return false;
        }

        int w = imageA.getWidth();
        int h = imageA.getHeight();

        for (int x = 0; x < h; h++) {
            for (int y = 0; y < w; y++) {
                if (imageA.getRGB(x, y) != imageB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

}
