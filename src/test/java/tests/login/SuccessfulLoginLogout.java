package tests.login;

//import data.CommonString;

import annotations.Jira;
import data.CommonString;
import data.Time;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import listeners.TestListener;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.JiraPolicy;
import utils.RestApiUtils;

import static data.Groups.LOGIN;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;


@Listeners(TestListener.class)
@Jira(jiraID = "JIRA00001")
@Test(groups = {REGRESSION, SANITY, LOGIN})
public class SuccessfulLoginLogout extends BaseTestClass {

    /*If we want to you all the benefits from ITestContext and
     *ITestResults and capturing screenshots while running tests
     *in parallel we need to have 1 test per class
     *because we have 1 web driver for all tests in the class
     * and there will be race condition for it. */


    private final String testName = this.getClass().getName();
    private WebDriver driver;

    public final String sJiraID = "JIRA00001A";
    private User user;

    private boolean isCreated = false;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + testName);
        driver = setUpDriver();
       // testContext.setAttribute("WebDriver", driver);

        testContext.setAttribute(testName + ".drivers", new WebDriver[]{driver});
        testContext.setAttribute(testName + ".jiraID", "JIRA00001B");


        user = User.createNewUniqueUser("successloginlogout");
        RestApiUtils.postUser(user);
        isCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
       // log.info("User: "+ user);
    }


    @JiraPolicy(logTicketReady=true)
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Successful Login")
    public void testSuccessfulLoginLogout() {

       // String username = PropertiesUtils.getAdminUsername();
       // String password = PropertiesUtils.getAdminPassword();
        String expectedLogoutSuccessMessage = CommonString.getLogoutSuccessMessage();

        log.debug("[START TEST] " + testName);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage
            .typeUsername(user.getUsername())
            .typePassword(user.getPassword())
            .clickLoginButton();

        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        loginPage = welcomePage.clickLogoutLink();
        DateTimeUtils.wait(Time.TIME_SHORT);

        String actualSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(actualSuccessMessage, "wrong expected", "Wrong logout success message");

        //Assert.fail("Test failed");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + testName);
        tearDown(driver, testResult);
        if (isCreated){
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Exception occurred in cleanUp(" + testName + ")! Message: " + e.getMessage());
        }
    }


}
