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

@Jira(jiraID = "JIRA00007")
@Test(groups = {API, USERS})
public class VerifyErrorGetNonExistingUser extends BaseTestClass {


    private final String testName = this.getClass().getName();
    private User user;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {

        log.debug("[SETUP TEST] " + testName);
        user = User.createNewUniqueUser("NonExistingUser");
        RestApiUtils.postUser(user);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Story("Successful Login")
    public void testApiForNonExistingUser() {

        Integer expectedStatusCode = CommonString.INTERNAL_SERVER_ERROR_CODE;
        String expectedError = CommonString.INTERNAL_SERVER_ERROR;
        String expectedException = CommonString.EXPECTED_EXCEPTION;
        String expectedMessage = String.format(CommonString.EXPECTED_MESSAGE_NON_EXISTING_USER,user.getUsername());
        String expectedPath = ApiCalls.createGetUserApiCall(user.getUsername());
        Date currentDateTime = DateTimeUtils.getCurrentDateTime();

        log.debug("[START TEST] " + testName);
        ApiError error = RestApiUtils.getUserApiError(user.getUsername());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(error.getStatus(),expectedStatusCode,"Wrong status code");
        softAssert.assertEquals(error.getError(),expectedError,"Wrong expected error");
        softAssert.assertEquals(error.getException(),expectedException,"Wrong exception error message");
        softAssert.assertEquals(error.getMessage(),expectedMessage,"Wrong expected message");
        softAssert.assertEquals(error.getPath(),expectedPath,"Wrong expected path");
        softAssert.assertTrue(DateTimeUtils.compareDateTimes(error.getTimestamp(),currentDateTime,2),"Wrong timestamp");
        softAssert.assertAll("Wrong Error Response Details!");
    }


}
