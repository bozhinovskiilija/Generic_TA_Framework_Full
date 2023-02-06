package tests.api;

import annotations.Jira;
import data.ApiCalls;
import data.CommonString;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import objects.ApiError;
import objects.User;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTestClass;
import utils.DateTimeUtils;
import utils.RestApiUtils;

import java.util.Date;

import static data.Groups.API;
import static data.Groups.USERS;


@Jira(jiraID = "JIRA00012")
@Test(groups = {API, USERS})
public class VerifyErrorGetUserNoPermission extends BaseTestClass {

    private final String testName = this.getClass().getName();
    private User user;

    private boolean isCreated = false;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {

        log.debug("[SETUP TEST] " + testName);
        user = User.createNewUniqueUser("NoPermissionUser");
        RestApiUtils.postUser(user);
        isCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Access API without proper permission")
    public void testVerifyErrorGetUserNoPermission() {

        log.debug("[START TEST] " + testName);

        Integer expectedStatusCode = 403;
        String expectedError = CommonString.SERVER_ERROR_FORBIDDEN;
        String expectedMessage = CommonString.EXPECTED_MESSAGE_ACCESS_DENIED;
        String expectedPath = ApiCalls.createGetUserApiCall(user.getUsername());

        ApiError error = RestApiUtils.getUserApiError(user.getUsername(),user.getUsername(),user.getPassword());
        Date currentDateTime = DateTimeUtils.getCurrentDateTime();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),expectedStatusCode,"Wrong status code");
        softAssert.assertEquals(error.getError(),expectedError,"Wrong expected error");
        softAssert.assertEquals(error.getMessage(),expectedMessage,"Wrong expected message");
        softAssert.assertEquals(error.getPath(),expectedPath,"Wrong expected path");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(),currentDateTime,10),"Wrong timestamp");
        softAssert.assertAll("Wrong Error Response Details!");
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
            RestApiUtils.deleteUser(user.getUsername());
        } catch (AssertionError | Exception e) {
            log.error("Exception occurred in cleanUp(" + testName + ")! Message: " + e.getMessage());
        }
    }


}
