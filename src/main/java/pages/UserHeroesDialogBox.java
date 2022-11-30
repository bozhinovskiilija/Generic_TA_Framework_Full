package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class UserHeroesDialogBox extends BasePageClass{

    //Locators
    private final String userHeroesDialogBoxLocatorString = "//div[@id='heroesModal']";
    private final By userHeroesDialogBox = By.id("heroesModal");
    private final By getUserHeroesDialogBoxTitleLocator = By.xpath(userHeroesDialogBoxLocatorString+"//h4[contains(@class,'modal-title')]");
    private final By closeButtonLocator = By.xpath(userHeroesDialogBoxLocatorString + "//button[contains(@class,'btn-default')]");

    //Constructor
    public UserHeroesDialogBox(final WebDriver driver) {
        super(driver);
    }


    public UserHeroesDialogBox verifyUserHeroesDialogBox() {
        log.debug("verifyUserHeroesDialogBox()");
        Assert.assertTrue(isUserHeroesDialogBoxOpened(Time.TIME_SHORT), "User heroes dialog box is not opened!");
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isUserHeroesDialogBoxOpened(int timeout) {
        return isWebElementVisible(userHeroesDialogBox, timeout);
    }

    private boolean isAddUserDialogBoxClosed(int timeout){
        return isWebElementInvisible(userHeroesDialogBox,timeout);
    }

    public boolean isDialogBoxTitleDisplayed(){
        log.debug("isDialogBoxTitleDisplayed()");
        return isWebElementDisplayed(getUserHeroesDialogBoxTitleLocator);
    }

    public String getDialogBoxTitle(){
        log.debug("getDialogBoxTitle()");
        Assert.assertTrue(isDialogBoxTitleDisplayed(),"Dialog box title is not displayed");
        WebElement dialogBoxTitle = getWebElement(getUserHeroesDialogBoxTitleLocator);
        return getTextFromWebElement(dialogBoxTitle);
    }

    public boolean isCloseButtonDisplayed() {
        log.debug("isCloseButtonDisplayed()");
        return isWebElementDisplayed(closeButtonLocator);
    }

    public UsersPage clickCloseButton() {
        log.debug("clickCloseButton()");
        Assert.assertTrue(isCloseButtonDisplayed(), "Close button is NOT displayed on User heroes Dialog box");
        WebElement closeButton = getWebElement(closeButtonLocator, Time.TIME_SHORTER);
        clickOnWebElement(closeButton);
        Assert.assertTrue(isAddUserDialogBoxClosed(Time.TIME_SHORTER),"User heroes dialog box is NOT closed!");
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }


}
