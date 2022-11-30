package tests.users;

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
import pages.AddUserDialogBox;
import pages.LoginPage;
import pages.UserHeroesDialogBox;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import static data.Groups.LOGIN;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

@Test(groups = {REGRESSION, SANITY, Groups.USERS})
public class AddNewUserTest extends BaseTestClass {


    private final String sTestName = this.getClass().getName();
    private WebDriver driver;

    String username;
    String password;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();

        username = PropertiesUtils.getAdminUsername();
        password = PropertiesUtils.getAdminPassword();
    }


    @Test
    public void testAddingNewUser() {

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(username, password);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        UsersPage usersPage = welcomePage.clickUsersTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        //Check if some user is present in users table
        log.info("Is username dedoje exists? " + usersPage.isUserPresentInUsersTable("dedoje"));
        log.info("Is username fddefef exists?" + usersPage.isUserPresentInUsersTable("fddefef"));
        log.info("Display name" + usersPage.getDisplayNameInUsersTable("dedoje"));
        log.info("Number of heroes: " + usersPage.getHeroCountInUsersTable("dedoje"));
        log.info("Number of users is users table is: "+ usersPage.getNumberOfRowsInUsersTable());


        UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable("dedoje");
        DateTimeUtils.wait(Time.TIME_SHORTER);

        usersPage = userHeroesDialogBox.clickCloseButton();
        DateTimeUtils.wait(Time.TIME_SHORTER);

        log.info("Number of users is users table is: "+ usersPage.getNumberOfRowsInUsersTable());

        // Assert.assertTrue(usersPage.isUserPresentInUsersTable("dedoje"),"Username does not exists");

        // AddUserDialogBox addNewUserDialogBox = usersPage.clickAddNewUsersButton();
        // DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        //
        // addNewUserDialogBox.typeUsername("user12345");
        // DateTimeUtils.wait(Time.TIME_SHORT);
        //
        // //users page dom structure now is refreshed
        // usersPage= addNewUserDialogBox.clickCancelButton();
        // DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
        //
        // usersPage.clickLogoutLink();

    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
    }


}
