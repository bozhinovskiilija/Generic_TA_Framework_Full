package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.DateTimeUtils;

import java.util.Date;

public class UserDetailsDialogBox extends BasePageClass{

    //Locators
    private final String userDetailsDialogBoxLocatorString = "//div[@id='userModal']";
    private final By userDetailsDialogBox = By.id("userModal");
    private final By getUserDetailsDialogBoxTitleLocator = By.xpath(userDetailsDialogBoxLocatorString+"//h4[contains(@class,'modal-title')]");
    private final By closeButtonLocator = By.xpath(userDetailsDialogBoxLocatorString + "//button[contains(@class,'btn-default')]");
    private final By createdAtText = By.xpath("locatorname");

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

    public boolean isCreatedAtTextDisplayed(){
        log.debug("isCreatedAtTextDisplayed()");
        return isWebElementDisplayed(createdAtText);
    }

    public String getCreatedAtText(){
        log.debug("getCreatedAtText()");
        Assert.assertTrue(isCreatedAtTextDisplayed(),"Created at text is not displayed");
        WebElement createdAtTextWebElement = getWebElement(createdAtText);
        return getTextFromWebElement(createdAtTextWebElement);
    }

    public Date getCreatedAtDate(){
        log.debug("getCreatedAtDate()");
        String dateTime = getCreatedAtText();
        return DateTimeUtils.getParsedDateTime(dateTime,"dd.MM.yyyy. HH:mm");


    }

}
