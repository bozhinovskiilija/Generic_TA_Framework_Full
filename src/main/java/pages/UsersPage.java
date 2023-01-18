package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class UsersPage extends CommonLoggedInPage{

    // Page Url Path
    private final String USERS_PAGE_URL = getPageUrl(PageUrlPaths.USERS_PAGE);

    //Locators
    private final By addNewUsersButton = By.xpath("//a[contains(@class,'btn-info') and contains(@onclick,'openAddUserModal')]");
    private final By usersTableLocator = By.id("users-table");

    private final By searchTextBoxLocator = By.id("search");
    private final By searchButtonLocator = By.xpath("//form[@id='searchForm']//i[contains(@class,'glyphicon-search')]");



    // //table[@id='users-table']//tbody//td[1]/self::td[text()='dedoje']/following-sibling::td[1]

    //complex parametrized locator (dynamic created locators)
    private String createXpathForUsernameInUsersTable(String username){
        return ".//tbody//td[1]/self::td[text()='"+username+"']";
    }

    private String createXpathForDisplayNameInUsersTable(String username){
        return createXpathForUsernameInUsersTable(username) + "/following-sibling::td[1]";
    }

    private String createXpathForHeroCountInUsersTable(String username){
        return createXpathForUsernameInUsersTable(username) + "/following-sibling::td[2]";
    }

    private String createXpathForUserIconsInUsersTable(String username){
        return createXpathForUsernameInUsersTable(username) + "/following-sibling::td[3]";
    }

    private String createXpathForUserDetailIconInUsersTable(String username){
        return createXpathForUserIconsInUsersTable(username) + "//a[contains(@class,'btn-info')]";
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

    public int getNumberOfRowsInUsersTable(){
        log.debug("getNumberOfRowsInUsersTable()");
        WebElement userTable = getWebElement(usersTableLocator);
        // dot in (.//tbody/tr) means start from the parent element
        String xpath = ".//tbody/tr";
        List<WebElement> usersTableRows = getNestedWebElements(userTable,By.xpath(xpath));
        return usersTableRows.size();
    }

    //https://www.w3schools.com/xml/xpath_axes.asp

    public boolean isUserPresentInUsersTable(String username){
        log.debug("isUserPresentInUsersTable()");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xPath = createXpathForUsernameInUsersTable(username);

        return isNestedWebElementDisplayed(usersTable,By.xpath(xPath));

    }

    public String getDisplayNameInUsersTable(String username){
        log.debug("getDisplayNameInUsersTable()");
        Assert.assertTrue(isUserPresentInUsersTable(username),"User: '"+username+"' is not present in Users table");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xpath = createXpathForDisplayNameInUsersTable(username);
        WebElement displayName = getNestedWebElement(usersTable,By.xpath(xpath));
        return getTextFromWebElement(displayName);
    }

    private WebElement getHeroCountLinkWebElementInUsersTable(String username){

        Assert.assertTrue(isUserPresentInUsersTable(username),"User: '"+username+"' is NOT present in Users table");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xpath = createXpathForHeroCountInUsersTable(username);
        return getNestedWebElement(usersTable,By.xpath(xpath));

    }

    public int getHeroCountInUsersTable(String username){
        log.debug("getHeroCountInUsersTable("+username+")");
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(username);
        return Integer.parseInt(getTextFromWebElement(heroCountLink));
    }

    public UserHeroesDialogBox clickHeroCountLinkInUsersTable(String username){
        log.debug("clickHeroCountLinkInUsersPage("+username+")");
        WebElement heroCountLink = getHeroCountLinkWebElementInUsersTable(username);
        clickOnWebElement(heroCountLink);

        //return new instance of User Hero Page
        UserHeroesDialogBox userHeroesDialogBox = new UserHeroesDialogBox(driver);
        return userHeroesDialogBox.verifyUserHeroesDialogBox();

    }

    public boolean isUserDetailsIconIsPresentInUsersTable(String username){
        log.debug("isUserDetailsIconIsPresentInUsersTable("+username+")");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xpath = createXpathForUserDetailIconInUsersTable(username);
        return isNestedWebElementDisplayed(usersTable,By.xpath(xpath));
    }

    public UserDetailsDialogBox clickUserDetailsIconInUsersTable(String username){
        log.debug("clickUserDetailsIconInUsersTable("+username+")");
        Assert.assertTrue(isUserDetailsIconIsPresentInUsersTable(username),"User detail icon is not present in Users table for user '"+username+"' ! ");
        WebElement usersTable = getWebElement(usersTableLocator);
        String xpath = createXpathForUserDetailIconInUsersTable(username);
        WebElement userDetailsIcon = getNestedWebElement(usersTable,By.xpath(xpath));
        clickOnWebElement(userDetailsIcon);
        //return UserDetailsDialogBox
        UserDetailsDialogBox userDetailsDialogBox = new UserDetailsDialogBox(driver);
        return userDetailsDialogBox.verifyUserDetailsDialogBox();

    }

    public boolean isSearchTextBoxDisplayed(){
        log.debug("isSearchTextBoxDisplayed()");
        return isWebElementDisplayed(searchTextBoxLocator);
    }

    public UsersPage typeSearchText(String text){
        log.debug("typeSearchText()");
        Assert.assertTrue(isSearchTextBoxDisplayed(), "'Search text box' is NOT displayed on Users Page");
        WebElement searchTextBox = getWebElement(searchTextBoxLocator);
        clearAndTypeTextToWebElement(searchTextBox,text);
        return this;
    }

    public String getSearchText(){
        log.debug("getSearchText()");
        Assert.assertTrue(isSearchTextBoxDisplayed(), "'Search text box' is NOT displayed on Users Page");
        WebElement searchTextBox = getWebElement(searchTextBoxLocator);
        return getValueFromWebElement(searchTextBox);
    }

    public boolean isSearchButtonDisplayed(){
        log.debug("isSearchButtonDisplayed()");
        return isWebElementDisplayed(searchButtonLocator);
    }

    public UsersPage clickSearchButton(){
        log.debug("clickSearchButton()");
        Assert.assertTrue(isSearchButtonDisplayed(), "'Search button' is NOT displayed on Heroes Page");
        WebElement searchButton = getWebElement(searchButtonLocator);
        clickOnWebElement(searchButton);
        UsersPage usersPage = new UsersPage(driver);
        return usersPage.verifyUsersPage();
    }

    public UsersPage search(String searchText){
        log.info("search(" + searchText + ")");
        typeSearchText(searchText);
        return clickSearchButton();

    }


}
