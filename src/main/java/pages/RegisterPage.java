package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class RegisterPage extends CommonLoggedOutPage{

    // Page Url Path
    private final String REGISTER_PAGE_URL = getPageUrl(PageUrlPaths.REGISTER_PAGE);

    //Locator
    private final By usernameTextFieldLocator = By.id("username");

    //Page Factory Locators

    /**
     * Page factory trys to locate the web element before using
     * in each method (always check for the fresh instance of the
     * web element before doing any actions with that element)
     *
     * It relies on implicit wait on
     *
     * You can't set explicit wait on page factory
     * solution: set implicit wait on driver level for each page
     */

    //@FindBy(xpath = "//input[@id='username']")
    //@CacheLookup //if you don't want to check everytime if element is present
    @FindBy(id = "username")
    private WebElement usernameTextField;

    @FindBy(id = "firstName")
    private WebElement firstNameTextField;

    @FindBy(id = "lastName")
    private WebElement lastNameTextField;

    @FindBy(id = "email")
    private WebElement emailTextField;

    @FindBy(id = "about")
    private WebElement aboutTextField;

    @FindBy(id = "secretQuestion")
    private WebElement secretQuestionTextField;

    @FindBy(id = "secretAnswer")
    private WebElement secretAnswerTextField;

    @FindBy(id = "password")
    private WebElement passwordTextField;

    @FindBy(id = "repassword")
    private WebElement confirmPasswordTextField;

    @FindBy(id = "usernameMessage")
    private WebElement usernameErrorMessage;

    @FindBy(id = "submitButton")
    private WebElement signUpButton;

    // Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
        log.trace("new RegisterPage()");
    }

    public RegisterPage open() {

        return open(true);
    }

    public RegisterPage open(boolean bVerify) {
        log.debug("Open RegisterPage (" + REGISTER_PAGE_URL + ")");
        openUrl(REGISTER_PAGE_URL);
        if (bVerify) {
            verifyRegisterPage();
        }
        return this;
    }

    public RegisterPage verifyRegisterPage() {
        log.debug("verifyRegisterPage()");
        waitForUrlChange(REGISTER_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isUsernameTextFieldDisplayed() {
        log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextField);
    }


    public RegisterPage typeUsername(String username) {
        log.info("typeUsername(" + username + ")");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on Register page");
        clearAndTypeTextToWebElement(usernameTextField, username);
        return this; //returning the current instance of LoginPage
    }


    public String getUsername() {
        log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on Register page");
        return getValueFromWebElement(usernameTextField);
    }

    public boolean isSignUpButtonDisplayed() {
        log.debug("isLoginButtonDisplayed()");
        return isWebElementDisplayed(signUpButton);
    }


    public boolean isSignUpButtonEnabled() {
        log.debug("isSignUpButtonEnabled()");
        Assert.assertTrue(isSignUpButtonDisplayed(), "SignUp button is not present on Register page");
        return isWebElementEnabled(signUpButton);
    }

    public LoginPage clickSignUpButton(){
        log.debug("clickSignUpButton()");
        Assert.assertTrue(isSignUpButtonEnabled(),"'SignUp' button is not enabled");
        clickOnWebElement(signUpButton);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }



}
