package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UsersPage extends CommonLoggedInPage{

    // Page Url Path
    private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

    //Locators
    private final By addNewUsersButton = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]");
    private final By usersTableLocator = By.id("users-table");


    // //table[@id='users-table']//tbody//td[1]/self::td[text()='dedoje']/following-sibling::td[1]

    private String createXpathForUsernameInUsersTable(String username){
        return "//tbody//td[1]/self::td[text()='"+username+"']";
    }

    private String createXpathForDisplayNameInUsersTable(String username){
        return createXpathForUsernameInUsersTable(username) + "/following-sibling::td[1]";
    }

    // Constructor
    public UsersPage(WebDriver driver) {
        super(driver);
        log.trace("new UsersPage()");
    }

    public UsersPage open() {
        return open(true);
    }

    public UsersPage open(boolean bVerify) {
        log.debug("Open UsersPage (" + USERS_PAGE_URL + ")");
        openUrl(USERS_PAGE_URL);
        if (bVerify) {
            verifyUsersPage();
        }
        return this;
    }

    public UsersPage verifyUsersPage() {
        log.debug("verifyUsersPage()");
        waitForUrlChange(USERS_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }


    public boolean isAddNewUsersButtonDisplayed() {
        log.debug("isAddNewUsersButtonDisplayed()");
        return isWebElementDisplayed(addNewUsersButton);
    }

    public AddUserDialogBox clickAddNewUsersButton() {
        log.debug("clickAddNewUsersButton()");
        Assert.assertTrue(isAddNewUsersButtonDisplayed(), "AddNewUsers button is not displayed on Users Page");
        WebElement NewUsersButton = getWebElement(addNewUsersButton, Time.TIME_SHORTER);
        clickOnWebElement(NewUsersButton);
        AddUserDialogBox addUserDialogBox = new AddUserDialogBox(driver);
        return addUserDialogBox.verifyAdduserDialogBox();
    }

    //https://www.w3schools.com/xml/xpath_axes.asp

    public boolean isUserPresentInUsersTable(String username){
        log.debug("isUserPresentInUsersTable()");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForUsernameInUsersTable(username);

        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));

    }


}
