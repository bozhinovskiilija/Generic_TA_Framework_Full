package stepDefs;

import data.Time;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;

public class ScenarioOutlineExampleSteps extends BaseTestClass {

    private WebDriver driver;

    @Before
    public void before() {
        driver = setUpDriver();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {


        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

    }


    @When("I submit the form with the following {string} and {string}")
    public void iSubmitTheFormWithTheFollowingAnd(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.typeUsername(username);
        loginPage.typePassword(password);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        loginPage = loginPage.clickLoginButtonWithNoProgress();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
    }


    @Then("The {string} should be displayed")
    public void theShouldBeDisplayed(String message) {

        LoginPage loginPage = new LoginPage(driver);

        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, message, "Wrong login error message");

    }


    @After()
    public void after() {

        driver.quit();

    }




}
