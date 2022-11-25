package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage extends CommonLoggedInPage{

    private final String HOME_PAGE_URL = getPageUrl(PageUrlPaths.HOME_PAGE);

    //it is different from all other pages
    private final By pageTitleLocator = By.xpath("//div[@class='my-jumbotron']/h1");

    public HomePage(WebDriver driver) {
        super(driver);
        log.trace("New Login Page");
    }

    public HomePage verifyHomePage() {
        log.debug("Verify login page");
        waitForUrlChange(HOME_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        //specific for each page(optional) - wait for the element to be present(for some problematic element in the page)
        return this;
    }

    public HomePage open(){
        return open(true);
    }

    public HomePage open(boolean verify) {
        log.debug("Open HomePage(" + HOME_PAGE_URL + ")");
        openUrl(HOME_PAGE_URL);
        if (verify) {
            verifyHomePage();
        }
        return this;
    }

    @Override
    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed()");
        return isWebElementDisplayed(pageTitleLocator);
    }
    @Override
    public String getPageTitle(){
        log.debug("isPageTitleDisplayed()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title element is not displayed");
        WebElement title = getWebElement(pageTitleLocator);
        return getTextFromWebElement(title);
    }



}
