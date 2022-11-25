package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class AddUserDialogBox extends BasePageClass {

    //Locators
    private final String addUserDialogBoxLocatorString = "//div[@id='addUserModal']";
    private final By addUserDialogBoxLocator = By.id("addUserModal");
    private final By cancelButtonLocator = By.xpath(addUserDialogBoxLocatorString + "//button[contains(@class,'btn-default')]");

    @FindBy(id = "username")
    //@FindBy(xpath = "//input[@id='username']")
    private WebElement usernameTextField;

    public AddUserDialogBox(final WebDriver driver) {
        super(driver);
    }


    public AddUserDialogBox verifyAdduserDialogBox() {
        log.debug("verifyAdduserDialogBox()");
        Assert.assertTrue(isAddUserDialogBoxOpened(Time.TIME_SHORT), "Add user dialog box is not opened!");
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }


    public boolean isAddUserDialogBoxOpened(int timeout) {
        return isWebElementVisible(addUserDialogBoxLocator, timeout);
    }

    private boolean isAddUserDialogBoxClosed(int timeout){
        return isWebElementInvisible(addUserDialogBoxLocator,timeout);
    }


    public boolean isUsernameTextFieldDisplayed() {
        log.debug("isUsernameTextFieldDisplayed()");
        return isWebElementDisplayed(usernameTextField);
    }

    public AddUserDialogBox typeUsername(String username) {
        log.info("typeUsername(" + username + ")");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on Add User Dialog Box");
        clearAndTypeTextToWebElement(usernameTextField, username);
        return this; //returning the current instance of LoginPage
    }


    public String getUsername() {
        log.debug("getUsername()");
        Assert.assertTrue(isUsernameTextFieldDisplayed(), "Username text field is not present on Add User Dialog Box");
        return getValueFromWebElement(usernameTextField);
    }


    public boolean isCancelButtonDisplayed() {
        log.debug("isCancelButtonDisplayed()");
        return isWebElementDisplayed(cancelButtonLocator);
    }

    public UsersPage clickCancelButton() {
        log.debug("clickCancelButton()");
        Assert.assertTrue(isCancelButtonDisplayed(), "Cancel button is not displayed on AddNewUser Dialog box");
        WebElement cancelButton = getWebElement(cancelButtonLocator, Time.TIME_SHORTER);
        clickOnWebElement(cancelButton);
        Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER),"Add new user dialog box is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }





}
