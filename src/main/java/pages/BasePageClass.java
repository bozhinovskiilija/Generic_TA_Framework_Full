package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.LoggerUtils;
import utils.PropertiesUtils;
import utils.WebDriverUtils;

import java.time.Duration;
import java.util.List;


//wrapper class for all wrapper methods (re-usable class)
//contains all basic selenium methods(all selenium logic)

public abstract class BasePageClass extends LoggerUtils {

    //this instance of web driver will be accessible form all child clases
    protected WebDriver driver;
    private static final String BASE_URL = PropertiesUtils.getBaseUrl();


    protected BasePageClass(WebDriver driver) {
        Assert.assertFalse(WebDriverUtils.hasDriverQuit(driver), "Driver instance has quit");
        this.driver = driver;
        PageFactory.initElements(driver, this);
        //lazy load concept (it is again implicit wait that override the existing one)
        //it will reflect on all web elements
        //PageFactory.initElements(new AjaxElementLocatorFactory(driver,10),this);
    }


    protected void openUrl(String url) {
        log.trace("OpenUrl(" + url + ")");
        driver.get(url);
    }


    public static String getBaseUrl() {

        return BASE_URL;
    }


    protected String getPageUrl(String path) {
        log.trace("getPageUrl(" + path + ")");
        return getBaseUrl() + path;
    }


    //waits for the presence of element(implicit wait)
    protected WebElement getWebElement(By locator) {
        log.trace("getWebElement(" + locator + ")");
        return driver.findElement(locator);
    }


    protected WebElement getWebElement(By locator, int timeout) {
        log.trace("getWebElement(" + locator + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    protected WebElement getNestedWebElement(WebElement parentElement, By childLocator) {
        log.trace("getNestedWebElement(" + parentElement + "," + childLocator + ")");
        return parentElement.findElement(childLocator);
    }


    protected WebElement getNestedWebElement(WebElement parentElement, By childLocator, int timeout) {
        log.trace("getNestedWebElement(" + parentElement + "," + childLocator + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);

        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentElement, childLocator));
    }


    protected List<WebElement> getWebElements(By locator) {
        log.trace("getWebElements()");
        return driver.findElements(locator);
    }


    protected List<WebElement> getNestedWebElements(WebElement parentElement, By locator) {
        log.trace("getNestedWebElements()");
        return parentElement.findElements(locator);
    }


    protected WebElement waitForWebElementToBeClickable(WebElement element, int timeout) {
        log.trace("waitForWebElementToBeClickable(" + element + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    protected WebElement waitForWebElementToBeVisible(By locator, int timeout) {
        log.trace("waitForWebElementToBeVisible(" + locator + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    protected WebElement waitForWebElementToBeVisible(WebElement element, int timeout) {
        log.trace("waitForWebElementToBeVisible(" + element + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    protected boolean waitForWebElementToBeInvisible(By locator, int timeout) {
        log.trace("waitForWebElementToBeInvisible(" + locator + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    protected boolean waitForWebElementToBeInvisible(WebElement element, int timeout) {
        log.trace("waitForWebElementToBeInvisible(" + element + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }


    protected Boolean waitForWebElementToBeSelected(WebElement element, int timeout) {
        log.trace("waitForWebElementToBeSelected(" + element + "," + timeout + ")");
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.elementToBeSelected(element));
    }


    protected boolean isWebElementVisible(By locator, int timeout) {
        log.trace("isWebElementVisible(" + locator + ", " + timeout + ")");
        try {
            WebElement element = waitForWebElementToBeVisible(locator, timeout);
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isWebElementVisible(WebElement element, int timeout) {
        log.trace("isWebElementVisible(" + element + ", " + timeout + ")");
        try {
            WebElement webElement = waitForWebElementToBeVisible(element, timeout);
            return webElement != null;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @param locator
     * @param timeout
     * @return
     */
    protected boolean isWebElementInvisible(By locator, int timeout) {
        log.trace("isWebElementInvisible(" + locator + ", " + timeout + ")");
        try {
            return waitForWebElementToBeInvisible(locator, timeout);

        } catch (Exception e) {
            return true;
        }
    }


    protected boolean isWebElementInvisible(WebElement element, int timeout) {
        log.trace("isWebElementInvisible(" + element + ", " + timeout + ")");
        try {
            return waitForWebElementToBeInvisible(element, timeout);
        } catch (Exception e) {
            return false;
        }
    }


    protected void typeTextToWebElement(WebElement element, String text) {
        log.trace("typeTextToWebElement(" + element + ")");
        element.sendKeys(text);
    }


    protected void clearAndTypeTextToWebElement(WebElement element, String text) {
        log.trace("clearAndTypeTextToWebElement(" + element + ")");
        element.clear();
        element.sendKeys(text);
    }


    protected void clickOnWebElement(WebElement element) {
        log.trace("clickOnWebElement(" + element + ")");
        element.click();
    }


    protected void clickOnWebElement(WebElement element, int timeout) {
        log.trace("clickOnWebElement(" + element + "," + timeout + ")");
        WebElement webElement = waitForWebElementToBeClickable(element, timeout);
        webElement.click();
    }


    protected void clickOnWebElementJS(WebElement element) {
        log.trace("clickOnWebElementJS(" + element + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].click();", element);
    }


    protected String getTextFromWebElement(WebElement element) {
        log.trace("getTextFromWebElement(" + element + ")");
        return element.getText();
    }


    protected String getAttributeFromWebElement(WebElement element, String attribute) {
        log.trace("getAttributeFromWebElement(" + element + "," + attribute + ")");
        return element.getAttribute(attribute);
    }


    protected String getValueFromWebElement(WebElement element) {
        log.trace("getAttributeFromWebElement(" + element + ")");
        return getAttributeFromWebElement(element, "value");
    }


    protected String getValueFromWebElementJS(WebElement element) {
        log.trace("getAttributeFromWebElement(" + element + ")");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].value", element);
    }


    protected boolean isWebElementDisplayed(By locator) {
        log.trace("getWebElement(" + locator + ")");
        try {
            WebElement element = getWebElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isWebElementDisplayed(WebElement element) {

        log.trace("getWebElement(" + element + ")");
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    //used for page factory
    //workaround for setting additional wait in page factory
    protected boolean isWebElementDisplayed(WebElement element, int timeout) {

        log.trace("getWebElement(" + element + ", " + timeout + ")");
        try {
            //override default implicit wait
            WebDriverUtils.setImplicitWait(driver, timeout);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            log.info("");
            WebDriverUtils.setImplicitWait(driver, Time.IMPLICIT_TIMEOUT);
        }
    }


    public boolean isNestedWebElementDisplayed(WebElement parentElement, By locator) {
        log.trace("isNestedWebElementDisplayed(" + parentElement + ", " + locator + ")");
        try {
            WebElement element = getWebElement(locator);
            return element.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isWebElementEnabled(WebElement element) {
        log.trace("isWebElementEnabled(" + element + ")");
        return element.isEnabled();
    }


    protected boolean isWebElementEnabled(WebElement element, int timeout) {
        log.trace("isWebElementEnabled(" + element + ", " + timeout + ")");
        try {
            WebElement webElement = waitForWebElementToBeClickable(element, timeout);
            return webElement != null;

        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isWebElementSelected(WebElement element) {
        log.trace("isWebElementSelected(" + element + ")");
        return element.isSelected();
    }


    protected boolean isWebElementSelected(WebElement element, int timeout) {
        log.trace("isWebElementSelected(" + element + ", " + timeout + ")");
        try {
            return waitForWebElementToBeSelected(element, timeout);

        } catch (Exception e) {
            return false;
        }
    }


    protected String getFirstSelectedOptionOnWebElement(WebElement element) {
        log.trace("getFirstSelectedOptionOnWebElement(" + element + ")");
        Select select = new Select(element);
        WebElement selectedOption = select.getFirstSelectedOption();
        return getTextFromWebElement(selectedOption);
    }


    protected boolean isOptionPresentOnWebElement(WebElement element, String option) {
        log.trace("getFirstSelectedOptionOnWebElement(" + element + "," + option + ")");
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        boolean isPresent = false;
        for(WebElement e : options){
            if(getValueFromWebElement(e).equals(option)){
                isPresent=true;
                break;
            }
        }
        return isPresent;
    }

    protected void selectOptionOnWebElement(WebElement element, String option){
        log.trace("selectOptionOnWebElement(" + element + "," + option + ")");
        Select select = new Select(element);
        select.selectByValue(option);

    }


    private WebDriverWait getWebDriverWait(int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }


    protected Boolean waitForUrlChange(String expectedUrl, int timeout) {
        log.trace("waitForUrlChange(" + expectedUrl + ")", "timeout " + timeout);
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(ExpectedConditions.urlContains(expectedUrl));
    }


    protected Boolean waitUntilPageIsReady(int timeout) {
        WebDriverWait wait = getWebDriverWait(timeout);
        return wait.until(
            driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }


    protected void moveMouseToWebElement(WebElement element){
        log.trace("moveMouseToWebElement(" + element  + ")");

        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    protected void doDragAndDrop(WebElement source, WebElement destination){
        log.trace("doDragAndDrop(" + source  +", "+ destination +  ")");
        Actions action = new Actions(driver);
        action.dragAndDrop(source,destination).perform();
        //if you don't have the destination element
        //action.dragAndDropBy(source,10,20).perform();
    }

}
