package tests.api;

import annotations.Jira;
import data.Groups;
import objects.User;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

@Jira(jiraID="JIRA0002", owner = "Users Team")
@Test(groups = {Groups.REGRESSION, Groups.SANITY, Groups.API, Groups.USERS})
public class VerifyApiPostGetDeleteUsers extends BaseTestClass {

    private final String sTestName = this.getClass().getName();

    private User user;
    private boolean bCreated = false;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] " + sTestName);
        user = User.createNewUniqueUser("UsersApiCalls");
    }

    @Test
    public void testVerifyApiPostGetDeleteUsers() {

        Assert.assertFalse(RestApiUtils.checkIfUserExists(user.getUsername()), "User '" + user.getUsername() + "' should NOT exist!");

        RestApiUtils.postUser(user);
        user.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        Assert.assertTrue(RestApiUtils.checkIfUserExists(user.getUsername()), "User '" + user.getUsername() + "' is NOT created!");
        bCreated = true;

        User createdUser = RestApiUtils.getUser(user.getUsername());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createdUser.getUsername(), user.getUsername(), "Username is NOT correct!");
        softAssert.assertEquals(createdUser.getFirstName(), user.getFirstName(), "First Name is NOT correct!");
        softAssert.assertEquals(createdUser.getLastName(), user.getLastName(), "Last Name is NOT correct!");
        softAssert.assertEquals(createdUser.getEmail(), user.getEmail(), "Email is NOT correct!");
        softAssert.assertEquals(createdUser.getAbout(), user.getAbout(), "About is NOT correct!");
        softAssert.assertEquals(createdUser.getSecretQuestion(), user.getSecretQuestion(), "Secret Question is NOT correct!");
        softAssert.assertEquals(createdUser.getSecretAnswer(), user.getSecretAnswer(), "Secret Answer is NOT correct!");
        softAssert.assertEquals(createdUser.getHeroCount(), user.getHeroCount(), "Hero Count is NOT correct!");
        //softAssert.assertTrue(DateTimeUtils.compareDateTimes(createdUser.getCreatedAt(), user.getCreatedAt()), "Created At Date is NOT correct!");
        softAssert.assertAll("Created User details are NOT correct!");

        RestApiUtils.deleteUser(user.getUsername());
        Assert.assertFalse(RestApiUtils.checkIfUserExists(user.getUsername()), "User '" + user.getUsername() + "' is NOT deleted!");
        bCreated = false;

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST] " + sTestName);
        if(bCreated) {
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }

}
