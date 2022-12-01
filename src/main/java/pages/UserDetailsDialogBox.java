package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UserDetailsDialogBox extends BasePageClass{

    //Locators
    private final String userDetailsDialogBoxLocatorString = "//div[@id='userModal']";
    private final By userDetailsDialogBox = By.id("userModal");
    private final By getUserDetailsDialogBoxTitleLocator = By.xpath(userDetailsDialogBoxLocatorString+"//h4[contains(@class,'modal-title')]");
    private final By closeButtonLocator = By.xpath(userDetailsDialogBoxLocatorString + "//button[contains(@class,'btn-default')]");

    //Constructor
    public UserDetailsDialogBox(final WebDriver driver) {

        super(driver);
    }


    public UserDetailsDialogBox verifyUserDetailsDialogBox() {
        log.debug("verifyUserDetailsDialogBox()");
        Assert.assertTrue(isUserDetailsDialogBoxOpened(Time.TIME_SHORT), "User heroes dialog box is not opened!");
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isUserDetailsDialogBoxOpened(int timeout) {
        return isWebElementVisible(userDetailsDialogBox, timeout);
    }

    private boolean isUserDetailsDialogBoxClosed(int timeout){
        return isWebElementInvisible(userDetailsDialogBox,timeout);
    }

    public boolean isDialogBoxTitleDisplayed(){
        log.debug("isDialogBoxTitleDisplayed()");
        return isWebElementDisplayed(getUserDetailsDialogBoxTitleLocator);
    }

    public String getDialogBoxTitle(){
        log.debug("getDialogBoxTitle()");
        Assert.assertTrue(isDialogBoxTitleDisplayed(),"Dialog box title is not displayed");
        WebElement dialogBoxTitle = getWebElement(getUserDetailsDialogBoxTitleLocator);
        return getTextFromWebElement(dialogBoxTitle);
    }

    public boolean isCloseButtonDisplayed() {
        log.debug("isCloseButtonDisplayed()");
        return isWebElementDisplayed(closeButtonLocator);
    }

    public UsersPage clickCloseButton() {
        log.debug("clickCloseButton()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close button is NOT displayed on User Details Dialog box");
        WebElement closeButton = getWebElement(closeButtonLocator, Time.TIME_SHORTER);
        clickOnWebElement(closeButton);
        Assert.assertTrue(isUserDetailsDialogBoxClosed(Time.TIME_SHORTER),"User Details dialog box is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

}
