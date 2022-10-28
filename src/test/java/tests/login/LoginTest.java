package tests.login;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;

public class LoginTest extends BaseTestClass {

    @Test
    public void testExample1(){
        WebDriver driver = setUpDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(5);
        quitDriver(driver);

    }

}
