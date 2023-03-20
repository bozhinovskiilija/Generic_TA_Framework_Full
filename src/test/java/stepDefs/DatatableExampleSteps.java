package stepDefs;

import data.Time;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import tests.BaseTestClass;
import utils.DateTimeUtils;

import java.util.List;
import java.util.Map;

public class DatatableExampleSteps extends BaseTestClass {

    private WebDriver driver;

    @Before
    public void before() {
        driver = setUpDriver();
    }

    @When("I submit the form with the following")
    public void iSubmitTheFormWithTheFollowing(DataTable table) {


        List<Map<String, String>> data = table.asMaps(String.class,String.class);

        for (Map<String, String> form : data) {
            String usr = form.get("username");
            String pas = form.get("password");

            DateTimeUtils.wait(Time.TIME_DEMONSTRATION);
            LoginPage loginPage = new LoginPage(driver);
            loginPage.typeUsername(usr);
            loginPage.typePassword(pas);

            //loginPage = loginPage.clickLoginButtonWithNoProgress();
        }


        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

    }

}
