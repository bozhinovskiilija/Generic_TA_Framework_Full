package tests.register;

import annotations.Jira;
import data.CommonString;
import data.Groups;
import data.Time;
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
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.RegisterPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import java.util.Date;

import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

//@Listeners(TestListener.class)
@Jira(jiraID = "JIRA00026", owner = "Users Team")
@Test(groups = {REGRESSION, SANITY, Groups.USERS})
public class SuccessfulRegister extends BaseTestClass {

    private final String sTestName = this.getClass().getSimpleName();
    private WebDriver driver;
    private User user;
    private boolean isCreated = false;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();
        //testContext.setAttribute("WebDriver", driver);
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});
        user = User.createNewUniqueUser("SuccessfulRegister");
    }


    @Test
    public void testRegisterNewUser() {

        String expectedRegisterSuccessMessage = CommonString.getRegisterSuccessMessage();

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        RegisterPage registerPage = loginPage.clickCreateAccountLink();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        log.info("New user " + user);

        loginPage = registerPage.registerUser(user);
        isCreated = true;
        Date date = DateTimeUtils.getCurrentDateTime();
        user.setCreatedAt(date);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);


        String sActualRegisterSuccessMessage = loginPage.getSuccessMessage();
        Assert.assertEquals(sActualRegisterSuccessMessage, expectedRegisterSuccessMessage,
            "Wrong Register Success Message!");


        User savedUser = RestApiUtils.getUser(user.getUsername());

        log.info(user);
        log.info(savedUser);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(savedUser.getEmail(), user.getEmail(), "Email is NOT correct!");
        softAssert.assertEquals(savedUser.getFirstName(), user.getFirstName(), "First Name is NOT correct!");
        softAssert.assertEquals(savedUser.getLastName(), user.getLastName(), "Last Name is NOT correct!");
        softAssert.assertEquals(savedUser.getAbout(), user.getAbout(), "About Text is NOT correct!");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(savedUser.getCreatedAt(), user.getCreatedAt(), 8));
        softAssert.assertEquals(savedUser.getSecretQuestion(), user.getSecretQuestion(),
            "Secret Question is NOT correct!");
        softAssert.assertEquals(savedUser.getSecretAnswer(), user.getSecretAnswer(), "Secret Answer is NOT correct!");
        softAssert.assertEquals(savedUser.getHeroCount(), user.getHeroCount(), "Hero Count is NOT correct!");
        softAssert.assertAll("Wrong User Details are saved in Database for User '" + user.getUsername() + "'!");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if (isCreated) {
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
