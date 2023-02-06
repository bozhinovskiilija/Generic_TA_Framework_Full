package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.Screenshot;
import utils.ScreenshotUtils;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class ProfilePage extends CommonLoggedInPage{

    // Page Url Path
    private final String PROFILE_PAGE_URL = getPageUrl(PageUrlPaths.PROFILE_PAGE);

    //Locators
    private final By profileImageLocator = By.id("profile-img");


    // Constructor
    public ProfilePage(WebDriver driver) {
        super(driver);
        log.trace("new ProfilePage()");
    }

    public ProfilePage open() {
        return open(true);
    }

    public ProfilePage open(boolean bVerify) {
        log.debug("Open ProfilePage (" + PROFILE_PAGE_URL + ")");
        openUrl(PROFILE_PAGE_URL);
        if (bVerify) {
            verifyProfilePage();
        }
        return this;
    }

    public ProfilePage verifyProfilePage() {
        log.debug("verifyProfilePage()");
        waitForUrlChange(PROFILE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public BufferedImage getProfileImageSnapshot(){
        log.debug("getProfileImageSnapshot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        return ScreenshotUtils.takeSnapshotOfWebElement(driver,profileImage);
    }

    public Screenshot getProfileImageSnapshotWithAshot(){
        log.debug("getProfileImageSnapshotWithAshot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        return ScreenshotUtils.takeSnapshotOfWebElementAshot(driver,profileImage);
    }

    public ProfilePage saveProfileImageSnapshot(){
        log.debug("saveProfileImageSnapshot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        ScreenshotUtils.saveSnapshotOfWebElement(driver,profileImage,"profileImage");
        return this;
    }

    public ProfilePage saveProfileImageSnapshotWithAshot(){
        log.debug("saveProfileImageSnapshotWithAshot()");
        WebElement profileImage = getWebElement(profileImageLocator);
        ScreenshotUtils.saveSnapshotOfWebElementWithAshot(driver,profileImage,"profileImage");
        return this;
    }


}
