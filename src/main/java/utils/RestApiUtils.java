package utils;

import com.google.gson.Gson;
import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.User;
import org.testng.Assert;
import pages.BasePageClass;

public class RestApiUtils extends LoggerUtils {

    private static final String BASE_URL = BasePageClass.getBaseUrl();

    private static final String ADMIN_USERNAME = PropertiesUtils.getAdminUsername();

    private static final String ADMIN_PASSWORD = PropertiesUtils.getAdminPassword();


    //wrapper method (for different type of scenarios - positive/negative)
    private static Response checkIfUserExistsApiCall(String username, String authUser, String authPassword) {

        String apiCall = BASE_URL + ApiCalls.createCheckIfUserExistsApiCall(username);

        Response response = null;

        try {
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .header("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(apiCall);

        } catch (Exception e) {
            Assert.fail("Exception in checkIfUserExists(" + username + ") Api Call: " + e.getMessage());
        }

        return response;

    }


    public static boolean checkIfUserExists(String username, String authUser, String authPassword) {
        log.debug("checkIfUserExists(" + username + ")");
        Response response = checkIfUserExistsApiCall(username, authUser, authPassword);

        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();

        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in checkIfUserExists(" + username + ") Api Call! Response Body: " + sResponseBody);

        if (!(sResponseBody.equalsIgnoreCase("true") || sResponseBody.equalsIgnoreCase("false"))) {
            Assert.fail("Cannot convert response body '" + sResponseBody + "' to boolean value!");
        }

        return Boolean.parseBoolean(sResponseBody);
    }


    public static boolean checkIfUserExists(String sUsername) {
        return checkIfUserExists(sUsername, ADMIN_USERNAME, ADMIN_PASSWORD);
    }



    private static Response getUserApiCall(String username, String authUser, String authPassword) {
        String apiCall = BASE_URL + ApiCalls.createGetUserApiCall(username);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(apiCall);
        } catch (Exception e) {
            Assert.fail("Exception in getUserApiCall(" + username + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static String getUserJsonFormat(String username, String authUser, String authPassword) {
        log.debug("getUserJsonFormat(" + username + ")");

        Assert.assertTrue(checkIfUserExists(username, authUser, authPassword), "User '" + username + "' doesn't exist!");

        Response response = getUserApiCall(username, authUser, authPassword);

        int status = response.getStatusCode();

        String responseBody = response.getBody().asPrettyString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in getUser(" + username + ") Api Call! Response Body: " + responseBody);

        return responseBody;
    }

    public static String getUserJsonFormat(String username) {
        return getUserJsonFormat(username, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public static User getUser(String username, String authUser, String authPass) {
        log.debug("getUser(" + username + ")");
        String json = getUserJsonFormat(username, authUser, authPass);
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, User.class);
    }

    public static User getUser(String sUsername) {
        return getUser(sUsername, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

}
