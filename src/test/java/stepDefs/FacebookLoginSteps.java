// package stepDefs;
//
// import data.Time;
// import io.cucumber.datatable.DataTable;
// import io.cucumber.java.After;
// import io.cucumber.java.Before;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.FindBy;
// import tests.BaseTestClass;
//
// import java.util.List;
// import java.util.Map;
//
// public class FacebookLoginSteps extends BaseTestClass {
//
//     private WebDriver driver;
//
//
//     @Before
//     public void before() {
//         driver = setUpDriver();
//     }
//
//     @FindBy(id = "email")
//     private WebElement usernameTextField;
//
//     @FindBy(id = "pass")
//     private WebElement passwordTextField;
//
//
//
//    // private final By usernameTextFieldLocator = By.id("username");
//   //  private final By passwordTextFieldLocator = By.id("password");
//
//     @Given("I am on the registration page")
//     public void iAmOnTheRegistrationPage() {
//         driver.get("https://www.facebook.com/");
//     }
//
//     @When("I submit the form with the following")
//     public void iSubmitTheFormWithTheFollowing(DataTable table) throws InterruptedException {
//
//         List<Map<String, String>> data = table.asMaps();
//
//
//
//         for (Map<String, String> form : data) {
//             String usr = form.get("Username");
//             String pas = form.get("Password");
//
//             Thread.sleep(5000);
//
//             usernameTextField.sendKeys(usr);
//             passwordTextField.sendKeys(pas);
//         }
//     }
//
//     @Then("the details should be displayed on my profile page")
//     public void theDetailsShouldBeDisplayedOnMyProfilePage() {
//     }
//
//
//     @After()
//     public void after() {
//
//         driver.quit();
//
//     }
//
//
//
//
// }
