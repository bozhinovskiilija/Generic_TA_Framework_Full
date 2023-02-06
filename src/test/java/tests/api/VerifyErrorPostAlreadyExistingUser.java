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


@Jira(jiraID = "JIRA00014")
@Test(groups = {API, USERS})
public class VerifyErrorPostAlreadyExistingUser extends BaseTestClass {

    private final String testName = this.getClass().getName();
    private User user;

    private boolean isCreated = false;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {

        log.debug("[SETUP TEST] " + testName);
        user = User.createNewUniqueUser("AlreadyExistingUser");
        RestApiUtils.postUser(user);
        isCreated = true;
        user.setCreatedAt(RestApiUtils.getUser(user.getUsername()).getCreatedAt());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("API Error - Already existing user")
    public void testVerifyErrorPostAlreadyExistingUser() {

        log.debug("[START TEST] " + testName);

        Integer expectedStatusCode = 500;
        String expectedError = CommonString.INTERNAL_SERVER_ERROR;
        String expectedException = CommonString.EXPECTED_EXCEPTION;
        String expectedMessage = CommonString.EXPECTED_MESSAGE_ALREADY_EXISTING_USER;
        String expectedPath = ApiCalls.createPostUserApiCall();

        ApiError error = RestApiUtils.postUserApiError(user);
        Date currentDateTime = DateTimeUtils.getCurrentDateTime();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),expectedStatusCode,"Wrong status code");
        softAssert.assertEquals(error.getError(),expectedError,"Wrong expected error");
        softAssert.assertEquals(error.getException(),expectedException,"Wrong expected exception error");
        softAssert.assertEquals(error.getMessage(),expectedMessage,"Wrong expected message");
        softAssert.assertEquals(error.getPath(),expectedPath,"Wrong expected path");
        //softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(),currentDateTime,2),"Wrong timestamp");
        softAssert.assertAll("Wrong Error Response Details!");
    }


}
