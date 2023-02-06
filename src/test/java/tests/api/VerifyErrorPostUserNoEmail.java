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
public class VerifyErrorPostUserNoEmail extends BaseTestClass {

    private final String testName = this.getClass().getName();
    private User user;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {

        log.debug("[SETUP TEST] " + testName);
        user = User.createNewUniqueUser("PostUserNoEmail");
        user.clearEmail();

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("API Error - No email provided in post new user")
    public void testVerifyErrorPostUserNoEmail() {

        log.debug("[START TEST] " + testName);

        Integer expectedStatusCode = 500;
        String expectedError = CommonString.INTERNAL_SERVER_ERROR;
        String expectedException = CommonString.EXPECTED_EXCEPTION;
        String expectedMessage = CommonString.EXPECTED_MESSAGE_EMAIL_NOT_SPECIFIED;
        String expectedPath = ApiCalls.createPostUserApiCall();

        ApiError error = RestApiUtils.postUserApiError(user);

        Date currentDateTime = DateTimeUtils.getCurrentDateTime();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),expectedStatusCode,"Wrong status code");
        softAssert.assertEquals(error.getError(),expectedError,"Wrong expected error");
        softAssert.assertEquals(error.getException(),expectedException,"Wrong expected exception error");
        softAssert.assertEquals(error.getMessage(),expectedMessage,"Wrong expected message");
        softAssert.assertEquals(error.getPath(),expectedPath,"Wrong expected path");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(),currentDateTime,5),"Wrong timestamp");
        softAssert.assertAll("Wrong Error Response Details!");
    }

}
