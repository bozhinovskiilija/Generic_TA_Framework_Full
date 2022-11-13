// package tests.login;
//
// import data.CommonString;
// import data.Time;
// import org.openqa.selenium.WebDriver;
// import org.testng.Assert;
// import org.testng.annotations.Test;
// import pages.LoginPage;
// import pages.WelcomePage;
// import tests.BaseTestClass;
// import utils.DateTimeUtils;
// import utils.PropertiesUtils;
//
// public class LoginTest extends BaseTestClass {
//
//     @Test
//     public void testSuccessfulLoginLogout() {
//
//         WebDriver driver = setUpDriver();
//
//         boolean success = false;
//
//         try {
//             String username = PropertiesUtils.getAdminUsername();
//             String password = PropertiesUtils.getAdminPassword();
//             String expectedLogoutSuccessMessage = CommonString.getLogoutSuccessMessage();
//             DateTimeUtils.wait(Time.TIME_SHORT);
//
//             LoginPage loginPage = new LoginPage(driver);
//             loginPage.open();
//             DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//             WelcomePage welcomePage = loginPage
//                 .typeUsername(username)
//                 .typePassword(password)
//                 .clickLoginButton();
//
//             DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//             loginPage = welcomePage.clickLogoutLink();
//             DateTimeUtils.wait(Time.TIME_SHORT);
//
//             String actualSuccessMessage = loginPage.getSuccessLogoutMessage();
//             Assert.assertEquals(actualSuccessMessage, expectedLogoutSuccessMessage, "Wrong logout success message");
//             success = true;
//
//         } finally {
//             tearDown(driver, success);
//         }
//
//
//     }
//
//
//     @Test
//     public void testUnsuccessfulLoginWrongPassword() {
//
//         WebDriver driver = setUpDriver();
//         boolean success = false;
//
//         try {
//             String username = PropertiesUtils.getAdminUsername();
//             String password = PropertiesUtils.getAdminPassword() + "!";
//             String expectedErrorMessage = CommonString.getLoginErrorMessage();
//             DateTimeUtils.wait(Time.TIME_SHORT);
//
//             LoginPage loginPage = new LoginPage(driver);
//             loginPage.open();
//             DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//             loginPage.typeUsername(username);
//             loginPage.typePassword(password);
//             DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//             loginPage = loginPage.clickLoginButtonWithNoProgress();
//             DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
//
//             String actualErrorMessage = loginPage.getErrorMessage();
//             Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Wrong login error message");
//
//             success = true;
//
//         } finally {
//             tearDown(driver, success);
//         }
//
//
//     }
//
// }
