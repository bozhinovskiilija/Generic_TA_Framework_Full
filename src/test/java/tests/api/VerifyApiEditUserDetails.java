package tests.api;

import annotations.Jira;
import data.Groups;
import objects.Hero;
import objects.User;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.PropertiesUtils;
import utils.RestApiUtils;

@Jira(jiraID="JIRA0001", owner = "Users Team")
@Test(groups = {Groups.REGRESSION, Groups.API, Groups.USERS})
public class VerifyApiEditUserDetails extends BaseTestClass {

    private final String sTestName = this.getClass().getName();

    private User admin;
    private User newUser;
    private Hero newHero;
    private boolean bCreated = false;
    private String sNewFirstName;
    private String sNewLastName;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] " + sTestName);
        admin = User.readUserFromCSVFile(PropertiesUtils.getAdminUsername());
        newUser = User.createNewUniqueUser("ApiEditUserDetails");
        RestApiUtils.postUser(newUser);
        bCreated = true;
        newUser.setCreatedAt(RestApiUtils.getUser(newUser.getUsername()).getCreatedAt());
        newHero = Hero.createNewUniqueHero(newUser, "ApiEditUser");
        RestApiUtils.postHero(newHero);
        newHero.setCreatedAt(DateTimeUtils.getCurrentDateTime());
        newUser.addHero(newHero);
        sNewFirstName = "New" + newUser.getFirstName();
        sNewLastName = "New" + newUser.getLastName();
    }

    @Test
    public void testApiEditUserDetails() {

        User editUserDetails = User.createUserTemplate();
        editUserDetails.setUsername(newUser.getUsername());
        editUserDetails.setFirstName(sNewFirstName);
        editUserDetails.setLastName(sNewLastName);

        RestApiUtils.editUser(editUserDetails, admin.getUsername(), admin.getPassword());
        newUser.setFirstName(sNewFirstName);
        newUser.setLastName(sNewLastName);

        User editedUser = RestApiUtils.getUser(newUser.getUsername());
        Hero editedUserHero = newUser.getHero(newHero.getHeroName());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(editedUser.getUsername(), newUser.getUsername(), "Username is NOT correct!");
        softAssert.assertEquals(editedUser.getFirstName(), newUser.getFirstName(), "First Name is NOT correct!");
        softAssert.assertEquals(editedUser.getLastName(), newUser.getLastName(), "Last Name is NOT correct!");
        softAssert.assertEquals(editedUser.getEmail(), newUser.getEmail(), "Email is NOT correct!");
        softAssert.assertEquals(editedUser.getAbout(), newUser.getAbout(), "About is NOT correct!");
        softAssert.assertEquals(editedUser.getSecretQuestion(), newUser.getSecretQuestion(), "Secret Question is NOT correct!");
        softAssert.assertEquals(editedUser.getSecretAnswer(), newUser.getSecretAnswer(), "Secret Answer is NOT correct!");
        softAssert.assertEquals(editedUser.getHeroCount(), newUser.getHeroCount(), "Hero Count is NOT correct!");
        //softAssert.assertTrue(DateTimeUtils.compareDateTimes(editedUser.getCreatedAt(), newUser.getCreatedAt()), "Created At Date is NOT correct!");
        softAssert.assertEquals(editedUserHero.getHeroName(), newHero.getHeroName(), "Hero Name is NOT correct!");
        softAssert.assertEquals(editedUserHero.getHeroLevel(), newHero.getHeroLevel(), "Hero Level is NOT correct!");
        softAssert.assertEquals(editedUserHero.getHeroClass(), newHero.getHeroClass(), "Hero Class is NOT correct!");
        softAssert.assertEquals(editedUserHero.getUsername(), newHero.getUsername(), "Username is NOT correct!");
        //softAssert.assertTrue(DateTimeUtils.compareDateTimes(editedUserHero.getCreatedAt(), newHero.getCreatedAt()), "Hero Created At Date is NOT correct!");
        softAssert.assertAll("Edited User details are NOT correct!");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.info("[END TEST] " + sTestName);
        if (bCreated) {
            cleanUp();
        }
    }

    private void cleanUp() {
        log.debug("cleanUp()");
        try {
            RestApiUtils.deleteUser(newUser.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Cleaning up failed! Message: " + e.getMessage());
        }
    }

}
