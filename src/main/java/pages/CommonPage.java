package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class CommonPage extends BasePageClass{

    private final By pageTitleLocator = By.xpath("//div[contains(@class,'panel-title')]");

    protected CommonPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isPageTitleDisplayed(){
        log.debug("isPageTitleDisplayed()");
        return isWebElementDisplayed(pageTitleLocator);
    }
    public String getPageTitle(){
        log.debug("isPageTitleDisplayed()");
        Assert.assertTrue(isPageTitleDisplayed(),"Page title element is not displayed");
        WebElement title = getWebElement(pageTitleLocator);
        return getTextFromWebElement(title);
    }



}
