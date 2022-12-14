package tests.page_evaluation;

import data.CommonString;
import data.Groups;
import data.Time;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.ScreenshotUtils;
import utils.WebDriverUtils;


/*Check if all locators are still good*/


@Test(groups = {Groups.EVALUATION})
public class EvaluateLoginPage extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();
    }


    @Test
    public void testEvaluateLoginPageLocators() {


        log.debug("[START TEST] " + sTestName);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        //when we want to assert multiple things, we can't use basic assert
        SoftAssert softAssert =new SoftAssert();
        softAssert.assertTrue(loginPage.isUsernameTextFieldDisplayed(),"username text field is not displayed on Login Page");
        softAssert.assertTrue(loginPage.isPasswordTextFieldDisplayed(),"password text field is not displayed on Login Page");
        softAssert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed on Login Page");
        softAssert.assertTrue(loginPage.isCreateAccountLinkDisplayed(),"create account link is not displayed on Login Page");
        softAssert.assertTrue(loginPage.isResetPasswordLinkDisplayed(),"reset password link is not displayed on Login Page");
        softAssert.assertAll("At least one web element is not displayed on Login Page! Locators changed?");

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        //cleanUp();
    }

}
