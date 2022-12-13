// package tests.login;
//
// //import data.CommonString;
//
// import data.CommonString;
// import data.Time;
// import org.openqa.selenium.WebDriver;
// import org.testng.Assert;
// import org.testng.ITestContext;
// import org.testng.ITestResult;
// import org.testng.annotations.AfterMethod;
// import org.testng.annotations.BeforeMethod;
// import org.testng.annotations.Test;
// import pages.HeroesPage;
// import pages.HomePage;
// import pages.LoginPage;
// import pages.UsersPage;
// import pages.WelcomePage;
// import tests.BaseTestClass;
// import utils.DateTimeUtils;
// import utils.PropertiesUtils;
//
// import static data.Groups.LOGIN;
// import static data.Groups.REGRESSION;
// import static data.Groups.SANITY;
//
// // two driver example test -
// // good when we want to test chat application (chat with 2 users)
//
// @Test(groups = {REGRESSION, LOGIN})
// public class LoginTwoDriver extends BaseTestClass {
//
//     private final String sTestName = this.getClass().getName();
//
//     private WebDriver driver1;
//     private WebDriver driver2;
//
//     String adminUsername = PropertiesUtils.getAdminUsername();
//     String adminPassword = PropertiesUtils.getAdminPassword();
//
//     String endUserUsername;
//     String endUserPassword;
//
//
//     @BeforeMethod
//     public void setupTest(ITestContext testContext) {
//         log.debug("[SETUP TEST] " + sTestName);
//         driver1 = setUpDriver();
//         driver2 = setUpDriver();
//
//         adminUsername = PropertiesUtils.getAdminUsername();
//         adminPassword = PropertiesUtils.getAdminPassword();
//         endUserUsername = PropertiesUtils.getEndUserUsername();
//         endUserPassword = PropertiesUtils.getEndUserPassword();
//     }
//
//
//     @Test
//     public void testSuccessfulLoginLogout() {
//
//         log.debug("[START TEST] " + sTestName);
//
//         LoginPage loginPage1 = new LoginPage(driver1).open();
//         log.info("Login Page title: "+loginPage1.getPageTitle());
//         DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//         LoginPage loginPage2 = new LoginPage(driver2).open();
//         DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//         WelcomePage welcomePage1 = loginPage1.login(adminUsername, adminPassword);
//         DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//         WelcomePage welcomePage2 = loginPage2.login(endUserUsername, endUserPassword);
//         DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//         HomePage homePage= welcomePage1.clickHomeTab();
//         log.info("Home Page title: "+homePage.getPageTitle());
//
//         //first driver navigate to users pages
//         UsersPage usersPage1 = welcomePage1.clickUsersTab();
//         DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//         //second driver navigate to heros pages
//         HeroesPage heroesPage2 = welcomePage2.clickHeroesTab();
//         DateTimeUtils.wait(Time.TIME_MEDIUM);
//
//         Assert.fail("fail the test on purpose");
//
//     }
//
//
//     @AfterMethod(alwaysRun = true)
//     public void tearDownTest(ITestResult testResult) {
//         log.debug("[END TEST] " + sTestName);
//         tearDown(driver1, testResult,1);
//         tearDown(driver2, testResult,2);
//     }
//
//
// }
