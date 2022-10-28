package pages;

import data.PageUrlPaths;
import org.openqa.selenium.WebDriver;
import utils.PropertiesUtils;

public class LoginPage extends CommonLoggedOutPage{

    private final String LOGIN_PAGE_URL = PropertiesUtils.getBaseUrl() + PageUrlPaths.LOGIN_PAGE;

    private WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver=driver;
        log.trace("New Login Page");
    }

    public LoginPage open(){
        driver.get(LOGIN_PAGE_URL);
        return this;
    }

}
