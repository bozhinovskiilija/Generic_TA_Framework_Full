package tests.users;

import annotations.Jira;
import data.Groups;
import data.Time;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;
import pages.UserDetailsDialogBox;
import pages.UserHeroesDialogBox;
import pages.UsersPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import static data.Groups.SANITY;
import static data.Groups.USERS;

@Jira(jiraID = "JIRA00057", owner = "Users Team")
@Test(groups = {SANITY, USERS})
public class AddNewUserTest extends BaseTestClass {

    private final String testName = this.getClass().getName();
    private WebDriver driver;
    String username;
    String password;

    public User user;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + testName);
        driver = setUpDriver();
        testContext.setAttribute(testName + ".drivers", new WebDriver[]{driver});
        testContext.setAttribute(testName + ".jiraID", "JIRA00001B");

        username = PropertiesUtils.getAdminUsername();
        password = PropertiesUtils.getAdminPassword();
    }


    @Test
    public void testAddingNewUser() {

        log.debug("[START TEST] " + testName);

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
        log.info("Number of users is users table is: " + usersPage.getNumberOfRowsInUsersTable());


        UserHeroesDialogBox userHeroesDialogBox = usersPage.clickHeroCountLinkInUsersTable("dedoje");
        DateTimeUtils.wait(Time.TIME_SHORTER);

        usersPage = userHeroesDialogBox.clickCloseButtonToUsersPage();
        DateTimeUtils.wait(Time.TIME_SHORTER);

        log.info("Number of users is users table is: " + usersPage.getNumberOfRowsInUsersTable());

        UserDetailsDialogBox userDetailsDialogBox = usersPage.clickUserDetailsIconInUsersTable("dedoje");
        log.info("DialogBox title: " + userDetailsDialogBox.getDialogBoxTitle());
        DateTimeUtils.wait(Time.TIME_SHORTER);

        usersPage = userDetailsDialogBox.clickCloseButtonToUsersPage();

        AdminPage adminPage = new AdminPage(driver);

        adminPage = usersPage.clickAdminTab();
        DateTimeUtils.wait(Time.TIME_SHORTER);
        adminPage = adminPage.checkAllowUserToShareRegistrationCode();
        DateTimeUtils.wait(Time.TIME_SHORTER);
        adminPage = adminPage.uncheckAllowUserToShareRegistrationCode();
        DateTimeUtils.wait(Time.TIME_SHORTER);

        //Assert.assertTrue(usersPage.isUserPresentInUsersTable("dedoje"),"Username does not exists");

        // AddUserDialogBox addNewUserDialogBox = usersPage.clickAddNewUserButton();
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
        log.debug("[END TEST] " + testName);
        tearDown(driver, testResult);
    }

}
