// package stepDefs;
//
// import io.cucumber.java.After;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.When;
// import org.openqa.selenium.WebDriver;
// import org.testng.ITestResult;
// import tests.BaseTestClass;
//
// public class GoogleSearchSteps extends BaseTestClass {
//
//     private WebDriver driver;
//
//     @Given("Launch the browser")
//     public void launchTheBrowser() {
//         driver = setUpDriver();
//     }
//
//     @When("User write {string} in the browser")
//     public void userWriteInTheBrowser(String url) {
//         driver.get(url);
//     }
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
