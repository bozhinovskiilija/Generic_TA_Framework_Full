package pages;

import data.PageUrlPaths;
import data.Time;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AdminPage extends CommonLoggedInPage{

    // Page Url Path
    private final String ADMIN_PAGE_URL = getPageUrl(PageUrlPaths.ADMIN_PAGE);


    //Page Factory Locators
    @FindBy(id="usersAllowed")
    WebElement allowUserToShareRegistrationCodeCheckBox;

    // Constructor
    public AdminPage(WebDriver driver) {
        super(driver);
        log.trace("new AdminPage()");
    }

    public AdminPage open() {
        return open(true);
    }

    public AdminPage open(boolean bVerify) {
        log.debug("Open AdminPage (" + ADMIN_PAGE_URL + ")");
        openUrl(ADMIN_PAGE_URL);
        if (bVerify) {
            verifyAdminPage();
        }
        return this;
    }

    public AdminPage verifyAdminPage() {
        log.debug("verifyAdminPage()");
        waitForUrlChange(ADMIN_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isCheckBoxDisplayed(){
        log.debug("isCheckBoxDisplayed()");
        return isWebElementDisplayed(allowUserToShareRegistrationCodeCheckBox);
    }

    public boolean isCheckBoxChecked(){
        log.debug("isCheckBoxChecked()");
        Assert.assertTrue(isCheckBoxDisplayed(),"Check box is not present on Admin Page");
        return isWebElementSelected(allowUserToShareRegistrationCodeCheckBox);
    }

    public AdminPage checkAllowUserToShareRegistrationCode(){
        log.debug("checkAllowUserToShareRegistrationCode()");
        if(!isCheckBoxChecked()){
            clickOnWebElement(allowUserToShareRegistrationCodeCheckBox);
        }
        return this;
    }

    public AdminPage uncheckAllowUserToShareRegistrationCode(){
        log.debug("uncheckAllowUserToShareRegistrationCode()");
        if(isCheckBoxChecked()){
            clickOnWebElement(allowUserToShareRegistrationCodeCheckBox);
        }
        AdminPage adminPage = new AdminPage(driver);
        return adminPage.verifyAdminPage();
    }

}
