package tests.register;

import data.Groups;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddUserDialogBox;
import pages.LoginPage;
import pages.RegisterPage;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

@Test(groups = {REGRESSION, SANITY, Groups.USERS})
public class SuccessfulRegister extends BaseTestClass {


    private final String sTestName = this.getClass().getSimpleName();
    private WebDriver driver;

    //String username;
    //String password;

    private User user;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        //username = "user1";
        //password = "password123";

        user=User.createNewUniqueUser("SuccessfulRegister");
    }


    @Test
    public void testRegisterNewUser() {

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        RegisterPage registerPage = loginPage.clickCreateAccountLink();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        log.info("New user " + user);

        registerPage.typeUsername(user.getUsername());




        loginPage = registerPage.clickSignUpButton();
        DateTimeUtils.wait(Time.TIME_SHORT);


    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver,testResult);
    }

}
