package tests.practice;

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
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

public class VerifyDragAndDrop extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    private User user;
    private boolean bCreated = false;


    @BeforeMethod
    public void setUpTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);

        driver = setUpDriver();
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("UselessTooltip");
        RestApiUtils.postUser(user);
        bCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
    }

    @Test
    public void testDragAndDrop() {

        String expectedDragAndDropMessage = CommonString.TOOLTIP_TEXT;

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        PracticePage practicePage = welcomePage.clickPracticeTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertTrue(practicePage.isImagePresentInDragArea(),"Image is not present in drag area before drag and drop");
        softAssert1.assertFalse(practicePage.isImagePresentInDropArea(),"Image is  present in drop area before drag and drop");
        softAssert1.assertFalse(practicePage.isDragAndDropMessageDisplayed(),"Drag and drop message is present before drag and drop action");
        softAssert1.assertAll("Wrong content on practice page before drag and drop action");

        practicePage = practicePage.dragAndDropImage();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        SoftAssert softAssert2 = new SoftAssert();
        softAssert2.assertFalse(practicePage.isImagePresentInDragArea(),"Image is present in drag area after drag and drop");
        softAssert2.assertTrue(practicePage.isImagePresentInDropArea(),"Image is NOT present in drop area after drag and drop");
        softAssert2.assertTrue(practicePage.isDragAndDropMessageDisplayed(),"Drag and drop message is NOT present after drag and drop action");
        softAssert2.assertAll("Wrong content on practice page before drag and drop action");

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
