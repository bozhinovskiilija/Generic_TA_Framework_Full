package tests.profile;

import data.CommonString;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.PracticePage;
import pages.ProfilePage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;
import utils.ScreenshotUtils;

import java.awt.image.BufferedImage;

public class VerifyProfileImage extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);

        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("ProfileImage");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
    }

    @Test
    public void testVerifyProfileImage() {


        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        ProfilePage profilePage = welcomePage.clickProfileLink();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        //get expected image, cut the image from screenshot folder
        //into test/resources/image, use this image for comparison
        //when testing on laptop change setting for display to 100%
        //text size(by default it is 125% and screenshot is not correct)
        //after that comment this line

       // profilePage.saveProfileImageSnapshot();

        //profilePage.saveProfileImageSnapshotWithAshot();

        BufferedImage actualImage = profilePage.getProfileImageSnapshot();
        String expectedProfileImageFile = "ProfileImage.png";

       // Assert.assertTrue(ScreenshotUtils.compareSnapshotWithImage(actualImage,expectedProfileImageFile));
        Assert.assertTrue(ScreenshotUtils.compareSnapshotWithImageWithAshot(profilePage.getProfileImageSnapshotWithAshot(),expectedProfileImageFile,0,0));


    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if(bCreated) {
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Exception occurred in cleanUp(" + sTestName + ")! Message: " + e.getMessage());
        }
    }

}
