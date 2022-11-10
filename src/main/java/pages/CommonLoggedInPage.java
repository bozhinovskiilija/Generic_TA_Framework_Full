package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonLoggedInPage extends BasePageClass {

    //Locators - navigation bar
    private final String headerLocatorString = "//header[@id='headContainer']";
    private final By samsaraLogoLocator = By.xpath(headerLocatorString + "//a[@class='navbar-brand']");
    private final By homeTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HOME_PAGE + "']");
    private final By usersTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.USERS_PAGE + "']");
    private final By heroesTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.HEROES_PAGE + "']");
    private final By apiTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.API_PAGE + "']");
    private final By galleryTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.GALLERY_PAGE + "']");
    private final By practiceTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PRACTICE_PAGE + "']");
    private final By adminTabLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.ADMIN_PAGE + "']");
    private final By profileLinkLocator = By.xpath(headerLocatorString + "//a[@href='" + PageUrlPaths.PROFILE_PAGE + "']");
    private final By logoutLinkLocator = By.xpath(headerLocatorString + "//a[contains(@href,'logoutForm.submit')]");




    //constructor
    public CommonLoggedInPage(final WebDriver driver) {
        super(driver);
    }


    public boolean isLogoutLinkDisplayed() {
        log.debug("isLogoutLinkDisplayed()");
        return isWebElementDisplayed(logoutLinkLocator);
    }


    public LoginPage clickLogoutLink() {
        log.debug("clickLogoutLink()");
        Assert.assertTrue(isLogoutLinkDisplayed(), "Logout link is NOT displayed on Navigation bar!");
        WebElement logoutLink = getWebElement(logoutLinkLocator,10);
        clickOnWebElement(logoutLink);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.verifyLoginPage();

    }

    public boolean isHomeTabDisplayed() {
        log.debug("isHomeTabDisplayed()");
        return isWebElementDisplayed(homeTabLocator);
    }

    public HomePage clickHomeTab() {
        log.debug("clickHomeTab()");
        Assert.assertTrue(isHomeTabDisplayed(), "Home tab button is NOT displayed on Navigation bar!");
        WebElement homeTab = getWebElement(homeTabLocator,10);
        clickOnWebElement(homeTab);
        HomePage homePage = new HomePage(driver);
        return homePage.verifyHomePage();

    }

    public String getHomeTabTitle(){
        log.debug("getHomeTabTitle()");
        Assert.assertTrue(isHomeTabDisplayed(), "Home tab button is NOT displayed on Navigation bar!");
        WebElement homeTab = getWebElement(homeTabLocator);
        return getTextFromWebElement(homeTab);
    }

}
