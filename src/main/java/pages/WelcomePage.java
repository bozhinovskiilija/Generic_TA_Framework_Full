package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.WebDriver;

public class WelcomePage extends CommonLoggedInPage{

    private final String WELCOME_PAGE_URL = getPageUrl(PageUrlPaths.WELCOME_PAGE);


    public WelcomePage(WebDriver driver) {
        super(driver);
        log.trace("new WelcomePage()");
    }

    public WelcomePage open() {

        return open(true);
    }


    public WelcomePage open(boolean verify) {
        log.debug("Open WelcomePage(" + WELCOME_PAGE_URL + ")");
        openUrl(WELCOME_PAGE_URL);
        if (verify) {
            verifyWelcomePage();
        }
        return this;
    }


    public WelcomePage verifyWelcomePage() {
        log.debug("Verify login page");
        waitForUrlChange(WELCOME_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        //specific for each page(optional) - wait for the element to be present(for some problematic element in the page)
        return this;
    }


}
