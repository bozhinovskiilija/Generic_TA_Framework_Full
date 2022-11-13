package tests.login;

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
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import static data.Groups.LOGIN;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

@Test(groups = {REGRESSION, SANITY, LOGIN})
public class SuccessfulLoginLogout extends BaseTestClass {

    /*If we want to you all the benefits from ITestContext and
     *ITestResults and capturing screenshots while running tests
     *in parallel we need to have 1 test per class
     *because we have 1 web driver for all tests in the class
     * and there will be race condition for it. */


    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();
    }

    @Test
    public void testSuccessfulLoginLogout() {

        String username = PropertiesUtils.getAdminUsername();
        String password = PropertiesUtils.getAdminPassword();
        String expectedLogoutSuccessMessage = CommonString.getLogoutSuccessMessage();

        log.debug("[START TEST] " + sTestName);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        // WelcomePage welcomePage = loginPage
        //     .typeUsername(username)
        //     .typePassword(password)
        //     .clickLoginButton();
        //
        // DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        //
        // loginPage = welcomePage.clickLogoutLink();
        // DateTimeUtils.wait(Time.TIME_SHORT);
        //
        // String actualSuccessMessage = loginPage.getSuccessLogoutMessage();
        // Assert.assertEquals(actualSuccessMessage, expectedLogoutSuccessMessage, "Wrong logout success message");

    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver,testResult);
    }

}
