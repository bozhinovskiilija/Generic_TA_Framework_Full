package pages;

import data.PageUrlPaths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonLoggedInPage extends CommonPage {

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

    public boolean isUsersTabDisplayed() {
        log.debug("isUsersTabDisplayed()");
        return isWebElementDisplayed(usersTabLocator);
    }

    public UsersPage clickUsersTab() {
        log.debug("clickUsersTab()");
        Assert.assertTrue(isUsersTabDisplayed(), "Users Tab is NOT displayed on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        clickOnWebElement(usersTab);
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    public String getUsersTabTitle() {
        log.debug("getUsersTabTitle()");
        Assert.assertTrue(isUsersTabDisplayed(), "Users Tab is NOT displayed on Navigation Bar!");
        WebElement usersTab = getWebElement(usersTabLocator);
        return getTextFromWebElement(usersTab);
    }

    public boolean isHeroesTabDisplayed() {
        log.debug("isHeroesTabDisplayed()");
        return isWebElementDisplayed(heroesTabLocator);
    }

    public HeroesPage clickHeroesTab() {
        log.debug("clickHeroesTab()");
        Assert.assertTrue(isHeroesTabDisplayed(), "Heroes Tab is NOT displayed on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        clickOnWebElement(heroesTab);
        HeroesPage heroesPage = new HeroesPage(driver);
        return heroesPage.verifyHeroesPage();
    }

    public String getHeroesTabTitle() {
        log.debug("getHeroesTabTitle()");
        Assert.assertTrue(isHeroesTabDisplayed(), "Heroes Tab is NOT displayed on Navigation Bar!");
        WebElement heroesTab = getWebElement(heroesTabLocator);
        return getTextFromWebElement(heroesTab);
    }

    public boolean isGalleryTabDisplayed() {
        log.debug("isGalleryTabDisplayed()");
        return isWebElementDisplayed(galleryTabLocator);
    }

    public GalleryPage clickGalleryTab() {
        log.debug("clickGalleryTab()");
        Assert.assertTrue(isGalleryTabDisplayed(), "Gallery Tab is NOT displayed on Navigation Bar!");
        WebElement galleryTab = getWebElement(galleryTabLocator);
        clickOnWebElement(galleryTab);
        GalleryPage galleryPage = new GalleryPage(driver);
        return galleryPage.verifyGalleryPage();
    }

    public String getGalleryTabTitle() {
        log.debug("getGalleryTabTitle()");
        Assert.assertTrue(isGalleryTabDisplayed(), "Gallery Tab is NOT displayed on Navigation Bar!");
        WebElement galleryTab = getWebElement(galleryTabLocator);
        return getTextFromWebElement(galleryTab);
    }

    public boolean isApiTabDisplayed() {
        log.debug("isApiTabDisplayed()");
        return isWebElementDisplayed(apiTabLocator);
    }

    public ApiPage clickApiTab() {
        log.debug("clickApiTab()");
        Assert.assertTrue(isApiTabDisplayed(), "Api Tab is NOT displayed on Navigation Bar!");
        WebElement apiTab = getWebElement(apiTabLocator);
        clickOnWebElement(apiTab);
        ApiPage apiPage = new ApiPage(driver);
        return apiPage.verifyApiPage();
    }

    public String getApiTabTitle() {
        log.debug("getApiTabTitle()");
        Assert.assertTrue(isApiTabDisplayed(), "Api Tab is NOT displayed on Navigation Bar!");
        WebElement apiTab = getWebElement(apiTabLocator);
        return getTextFromWebElement(apiTab);
    }

    public boolean isPracticeTabDisplayed() {
        log.debug("isPracticeTabDisplayed()");
        return isWebElementDisplayed(practiceTabLocator);
    }

    public PracticePage clickPracticeTab() {
        log.debug("clickPracticeTab()");
        Assert.assertTrue(isPracticeTabDisplayed(), "Practice Tab is NOT displayed on Navigation Bar!");
        WebElement practiceTab = getWebElement(practiceTabLocator);
        clickOnWebElement(practiceTab);
        PracticePage practicePage = new PracticePage(driver);
        return practicePage.verifyPracticePage();
    }

    public String getPracticeTabTitle() {
        log.debug("getPracticeTabTitle()");
        Assert.assertTrue(isPracticeTabDisplayed(), "Practice Tab is NOT displayed on Navigation Bar!");
        WebElement practiceTab = getWebElement(practiceTabLocator);
        return getTextFromWebElement(practiceTab);
    }

    public boolean isAdminTabDisplayed() {
        log.debug("isAdminTabDisplayed()");
        return isWebElementDisplayed(adminTabLocator);
    }

    public AdminPage clickAdminTab() {
        log.debug("clickAdminTab()");
        Assert.assertTrue(isAdminTabDisplayed(), "Admin Tab is NOT displayed on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        clickOnWebElement(adminTab);
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }

    public String getAdminTabTitle() {
        log.debug("getAdminTabTitle()");
        Assert.assertTrue(isAdminTabDisplayed(), "Admin Tab is NOT displayed on Navigation Bar!");
        WebElement adminTab = getWebElement(adminTabLocator);
        return getTextFromWebElement(adminTab);
    }

    public boolean isProfileLinkDisplayed() {
        log.debug("isProfileLinkDisplayed()");
        return isWebElementDisplayed(profileLinkLocator);
    }

    public ProfilePage clickProfileLink() {
        log.debug("clickProfileLink()");
        Assert.assertTrue(isProfileLinkDisplayed(), "Profile Link is NOT displayed on Navigation Bar!");
        WebElement profileLink = getWebElement(profileLinkLocator);
        clickOnWebElement(profileLink);
        ProfilePage profilePage = new ProfilePage(driver);
        return profilePage.verifyProfilePage();
    }

    public String getProfileLinkTitle() {
        log.debug("getProfileLinkTitle()");
        Assert.assertTrue(isProfileLinkDisplayed(), "Profile Link is NOT displayed on Navigation Bar!");
        WebElement profileLink = getWebElement(profileLinkLocator);
        return getTextFromWebElement(profileLink);
    }

    public String getLogoutLinkTitle() {
        log.debug("getLogoutLinkTitle()");
        Assert.assertTrue(isLogoutLinkDisplayed(), "Logout Link is NOT displayed on Navigation Bar!");
        WebElement logoutLink = getWebElement(logoutLinkLocator);
        return getTextFromWebElement(logoutLink);
    }



}
