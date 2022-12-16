package tests.heroes;

import data.CommonString;
import data.Time;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import listeners.TestListener;
import objects.Hero;
import objects.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddHeroDialogBox;
import pages.HeroesPage;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import static data.Groups.LOGIN;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

@Listeners(TestListener.class)
@Test(groups = {REGRESSION, SANITY, LOGIN})
public class AddNewHero extends BaseTestClass {

    private final String sTestName = this.getClass().getName();
    private WebDriver driver;
    private User user;
    private Hero hero;
    private boolean isCreated = false;


    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        log.debug("[SETUP TEST] " + sTestName);
        driver = setUpDriver();


        user = User.createNewUniqueUser("AddNewHero");
        RestApiUtils.postUser(user);
        isCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        log.info("User: "+ user);

        hero = Hero.createNewUniqueHero(user,"NewHero");

    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Adding New Hero")
    public void testAddNewHero() {

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);


        addHeroDialogBox.typeHeroName(hero.getHeroName());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        addHeroDialogBox.typeHeroLevel(hero.getHeroLevel().toString());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if (isCreated){
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Exception occurred in cleanUp(" + sTestName + ")! Message: " + e.getMessage());
        }
    }

}
