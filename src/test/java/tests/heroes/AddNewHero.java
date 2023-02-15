package tests.heroes;

import annotations.Jira;
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
import org.testng.asserts.SoftAssert;
import pages.AddHeroDialogBox;
import pages.HeroesPage;
import pages.LoginPage;
import pages.WelcomePage;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import java.util.Date;

import static data.Groups.HERO;
import static data.Groups.LOGIN;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

//@Listeners(TestListener.class)
@Jira(jiraID = "JIRA00009", owner = "Heroes Team")
@Test(groups = {REGRESSION, SANITY, HERO})
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
        testContext.setAttribute(sTestName + ".drivers", new WebDriver[]{driver});

        user = User.createNewUniqueUser("AddNewHero");
        RestApiUtils.postUser(user);
        isCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
        hero = Hero.createNewUniqueHero(user, "NewHero");

    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Adding New Hero")
    public void testAddNewHero() {

        log.debug("[START TEST] " + sTestName);

        LoginPage loginPage = new LoginPage(driver).open();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        WelcomePage welcomePage = loginPage.login(user);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        HeroesPage heroesPage = welcomePage.clickHeroesTab();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        AddHeroDialogBox addHeroDialogBox = heroesPage.clickAddNewHeroButton();
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        addHeroDialogBox.typeHeroName(hero.getHeroName());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        addHeroDialogBox.typeHeroLevel(hero.getHeroLevel());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        addHeroDialogBox.selectHeroClass(hero.getHeroClass());
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        heroesPage = addHeroDialogBox.clickSaveButton();
        Date currentDateTime = DateTimeUtils.getCurrentDateTime();
        hero.setCreatedAt(currentDateTime);
        DateTimeUtils.wait(Time.TIME_DEMONSTRATION);

        Assert.assertTrue(RestApiUtils.checkIfHeroExists(hero.getHeroName()), "Hero '" + hero.getHeroName() + "' is NOT created!");
        Hero savedHero = RestApiUtils.getHero(hero.getHeroName());

        SoftAssert softAssert1 = new SoftAssert();
        softAssert1.assertEquals(savedHero.getUsername(), hero.getUsername(), "Username is NOT correct!");
        softAssert1.assertEquals(savedHero.getHeroClass(), hero.getHeroClass(), "Hero Class is NOT correct!");
        softAssert1.assertEquals(savedHero.getHeroLevel(), hero.getHeroLevel(), "Hero Level is NOT correct!");
        //softAssert1.assertTrue(DateTimeUtils.compareDateTimes(savedHero.getCreatedAt(), hero.getCreatedAt(), 10), "CreatedAt Date is NOT correct!");
        softAssert1.assertAll("Hero Details for Hero '" + hero.getHeroName() + "' are not correct!");

        User savedUser = RestApiUtils.getUser(user.getUsername());
        log.info("Saved User: " + savedUser);

        Hero usersHero = savedUser.getHero(hero.getHeroName());
        SoftAssert softAssert2 = new SoftAssert();
        softAssert2.assertEquals(usersHero.getUsername(), hero.getUsername(), "Username is NOT correct!");
        softAssert2.assertEquals(usersHero.getHeroClass(), hero.getHeroClass(), "Hero Class is NOT correct!");
        softAssert2.assertEquals(usersHero.getHeroLevel(), hero.getHeroLevel(), "Hero Level is NOT correct!");
     //   softAssert2.assertTrue(DateTimeUtils.compareDateTimes(usersHero.getCreatedAt(), hero.getCreatedAt(), 10), "CreatedAt Date is NOT correct!");
        softAssert2.assertAll("Hero Details for Hero '" + hero.getHeroName() + "' are not correct!");

        Assert.fail("Test failed");


    }


    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        tearDown(driver, testResult);
        if (isCreated) {
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
