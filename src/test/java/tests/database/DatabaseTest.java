package tests.database;

import annotations.Jira;
import data.ApiCalls;
import data.CommonString;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import objects.ApiError;
import objects.DatabaseHero;
import objects.DatabaseUser;
import objects.User;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DatabaseUtils;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import java.util.Date;
import java.util.List;

import static data.Groups.API;
import static data.Groups.DATABASE;
import static data.Groups.USERS;

@Jira(jiraID = "JIRA00017")
@Test(groups = {DATABASE})
public class DatabaseTest extends BaseTestClass {

    private final String testName = this.getClass().getName();
    private User newUser;

     boolean isCreated = false;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {

        log.debug("[SETUP TEST] " + testName);

        newUser = User.createNewUniqueUser("NewDatabaseUser");
        RestApiUtils.postUser(newUser);
        isCreated = true;
        newUser.setCreatedAt(RestApiUtils.getUser(newUser.getUsername()).getCreatedAt());
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Basic Database tests")
    public void testDatabase() {

        log.debug("[START TEST] " + testName);

        String userID = DatabaseUtils.getUserID("finn");
        log.info("UserID: " + userID);

        List<String> usernameList = DatabaseUtils.getAllUsernames();
        log.info(usernameList);

        DatabaseUser databaseUser = DatabaseUtils.getDatabaseUser("finn");
        log.info(databaseUser);

        List<DatabaseHero> databaseHeroesList = DatabaseUtils.getDatabaseHeroesForUser(userID);
        for(DatabaseHero h : databaseHeroesList){
            log.info(h);
        }

        User user = DatabaseUtils.getUser("dedoje");
        log.info(user);


    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + testName);
        if (isCreated){
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(newUser.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Exception occurred in cleanUp(" + testName + ")! Message: " + e.getMessage());
        }
    }



}
