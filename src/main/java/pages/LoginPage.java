package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage extends CommonLoggedOutPage {

    private final String LOGIN_PAGE_URL = getPageUrl(PageUrlPaths.LOGIN_PAGE);
    //private final String LOGIN_PAGE_URL = "https://www.google.com";

    //locators(not web elements) for Login Page
    private final String loginBoxLocator = "//div[@id='loginbox']";
    private final By usernameTextFieldLocator = By.id("username");
    private final By passwordTextFieldLocator = By.id("password");

    //you use conatains if you have multiple classes in the class attribute
    private final By loginButtonLocator = By.xpath(loginBoxLocator + "//input[contains(@class,'btn-primary')]");

    //contains message for successful logout and successful register
    private final By successMessageLocator = By.xpath(
        loginBoxLocator + "//div[contains(@class,'alert-success')]");
    private final By loginErrorMessageLocator = By.xpath(loginBoxLocator + "//div[contains(@class,'alert-danger')]");

    private final By createAccountLinkLocator = By.xpath(
        loginBoxLocator + "//a[@href='" + PageUrlPaths.REGISTER_PAGE + "']");
    private final By resetPasswordLinkLocator = By.xpath(
        loginBoxLocator + "//a[@href='" + PageUrlPaths.RESET_PASSWORD_PAGE + "']");


    //private final By loginButtonLocator = By.cssSelector("div#loginbox input.btn-primary");
    //not good approach - because of stale element exception(element not in DOM yet, or DOM structure is changed)
    //  WebElement usernameTextField = driver.findElement(By.id("username"));


    //one instence of WebDriver for each test
    public LoginPage(WebDriver driver) {
        super(driver);
        log.trace("New Login Page");
    }


    public LoginPage open() {
        return open(false);
    }


    public LoginPage open(boolean verify) {
        log.debug("Open LoginPage(" + LOGIN_PAGE_URL + ")");
        openUrl(LOGIN_PAGE_URL);
        if (verify) {
            verifyLoginPage();
        }
        return this;
    }


    public LoginPage verifyLoginPage() {
        log.debug("Verify login page");
        waitForUrlChange(LOGIN_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        //specific for each page(optional) - wait for the element to be present(for some problematic element in the page)
        return this;
    }


    public boolean isUsernameTextFieldDisplayed() {
        log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextFieldLocator);
    }


    public LoginPage typeUsername(String username) {
        log.info("typeUsername(" + username + ")");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on login page");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(usernameTextField, username);
        return this; //returning the current instance of LoginPage
    }


    public String getUsername() {
        log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on login page");
        WebElement usernameTextField = getWebElement(usernameTextFieldLocator, Time.TIME_SHORTER);
        return getValueFromWebElement(usernameTextField);
    }


    public boolean isPasswordTextFieldDisplayed() {
        log.debug("isPasswordTextFieldDisplayed()");
        return isWebElementDisplayed(passwordTextFieldLocator);
    }


    public LoginPage typePassword(String password) {
        log.info("typePassword(" + password + ")");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on login page");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator, Time.TIME_SHORTER);
        clearAndTypeTextToWebElement(passwordTextField, password);
        return this;
    }


    public String getPassword() {
        log.debug("getPassword()");
        Assert.assertTrue(isPasswordTextFieldDisplayed(), "Password text field is not present on login page");
        WebElement passwordTextField = getWebElement(passwordTextFieldLocator, Time.TIME_SHORTER);
        return getValueFromWebElement(passwordTextField);
    }


    public boolean isLoginButtonDisplayed() {
        log.debug("isLoginButtonDisplayed()");
        return isWebElementDisplayed(loginButtonLocator);
    }


    public boolean isLoginButtonEnabled() {
        log.debug("isLoginButtonEnabled()");
        Assert.assertTrue(isLoginButtonDisplayed(), "Login button is not present on login page");
        WebElement loginButton = getWebElement(loginButtonLocator, Time.TIME_SHORTER);
        return isWebElementEnabled(loginButton, Time.TIME_SHORT);
    }


    private void clickLoginButtonNoVerification() {
        Assert.assertTrue(isLoginButtonEnabled(), "Login button is not enabled/clickable");
        WebElement loginButton = getWebElement(loginButtonLocator, Time.TIME_SHORTER);
        clickOnWebElement(loginButton);
    }


    public WelcomePage clickLoginButton() {
        log.debug("clickLoginButton()");
        clickLoginButtonNoVerification();
        WelcomePage welcomePage = new WelcomePage(driver);
        return welcomePage.verifyWelcomePage();
    }


    public LoginPage clickLoginButtonWithNoProgress() {
        log.debug("clickLoginButtonWithNoProgress()");
        clickLoginButtonNoVerification();
        //we need new instance of login page because the DOM structure is refreshed(error message is displayed)
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();
    }


    public boolean isSuccessLogoutMessageDisplayed() {
        log.debug("isSuccessLogoutMessageDisplayed()");
        return isWebElementDisplayed(successMessageLocator);
    }


    public String getSuccessMessage() {
        log.debug("getSuccessLogoutMessage()");
        Assert.assertTrue(isSuccessLogoutMessageDisplayed(), "Success logout message is not displayed");
        WebElement logoutMessage = getWebElement(successMessageLocator);
        return getTextFromWebElement(logoutMessage);
    }


    public boolean isErrorMessageDisplayed() {
        log.debug("isErrorMessageDisplayed()");
        return isWebElementDisplayed(loginErrorMessageLocator);
    }


    public String getErrorMessage() {
        log.debug("getErrorMessage()");
        Assert.assertTrue(isErrorMessageDisplayed(), "Error message is not displayed");
        WebElement errorMessage = getWebElement(loginErrorMessageLocator);
        return getTextFromWebElement(errorMessage);
    }


    public boolean isCreateAccountLinkDisplayed() {
        log.debug("isCreateAccountLinkDisplayed()");
        return isWebElementDisplayed(createAccountLinkLocator);
    }


    public RegisterPage clickCreateAccountLink() {
        log.debug("clickCreateAccountLink()");
        Assert.assertTrue(isCreateAccountLinkDisplayed(), "Create account link is NOT displayed on Login Page!");
        WebElement createAccountLink = getWebElement(createAccountLinkLocator, 10);
        clickOnWebElement(createAccountLink);
        RegisterPage registerPage = new RegisterPage(driver);
        return registerPage.verifyRegisterPage();

    }


    public String getCreateAccountLinkTitle() {
        log.debug("getCreateAccountLinkTitle()");
        Assert.assertTrue(isCreateAccountLinkDisplayed(), "Create account link is NOT displayed on Login Page!");
        WebElement createAccount = getWebElement(createAccountLinkLocator);
        return getTextFromWebElement(createAccount);
    }


    public boolean isResetPasswordLinkDisplayed() {
        log.debug("isResetPasswordLinkDisplayed()");
        return isWebElementDisplayed(resetPasswordLinkLocator);
    }


    public ResetPasswordPage clickResetPasswordLink() {
        log.debug("clickResetPasswordLink()");
        Assert.assertTrue(isResetPasswordLinkDisplayed(), "Reset password link is NOT displayed on Login Page!");
        WebElement resetPasswordLink = getWebElement(createAccountLinkLocator, 10);
        clickOnWebElement(resetPasswordLink);
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
        return resetPasswordPage.verifyResetPasswordPage();

    }


    public String getResetPasswordLinkTitle() {
        log.debug("getResetPasswordLinkTitle()");
        Assert.assertTrue(isResetPasswordLinkDisplayed(), "Reset password link is NOT displayed on Login Page!");
        WebElement resetPassword = getWebElement(resetPasswordLinkLocator);
        return getTextFromWebElement(resetPassword);
    }


    public WelcomePage login(String username, String password) {
        log.info("Login(" + username + "," + password + ")");
        typeUsername(username);
        typePassword(password);
        return clickLoginButton();
    }


}
