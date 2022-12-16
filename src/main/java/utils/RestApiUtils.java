package utils;

import com.google.gson.Gson;
import data.ApiCalls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.Hero;
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

        Assert.assertTrue(checkIfUserExists(username, authUser, authPassword),
            "User '" + username + "' doesn't exist!");

        Response response = getUserApiCall(username, authUser, authPassword);

        int status = response.getStatusCode();

        String responseBody = response.getBody().asPrettyString();
        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in getUser(" + username + ") Api Call! Response Body: " + responseBody);

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


    private static Response deleteUserApiCall(String sUsername, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createDeleteUserApiCall(sUsername);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().redirects().follow(false)
                .delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in deleteUserApiCall(" + sUsername + ") Api Call: " + e.getMessage());
        }
        return response;
    }


    public static void deleteUser(String sUsername, String authUser, String authPassword) {
        log.debug("deleteUser(" + sUsername + ")");
        Assert.assertTrue(checkIfUserExists(sUsername, authUser, authPassword), "User '" + sUsername + " doesn't exist!");
        Response response = deleteUserApiCall(sUsername, authUser, authPassword);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in deleteUser(" + sUsername + ") Api Call! Response Body: " + sResponseBody);
        log.debug("User Deleted: " + !checkIfUserExists(sUsername, authUser, authPassword));
    }

    public static void deleteUser(String sUsername) {
        deleteUser(sUsername, ADMIN_USERNAME, ADMIN_PASSWORD);
    }


    private static Response postUserApiCall(User user, String authUser, String authPassword) {
        String sApiCall = BASE_URL + ApiCalls.createPostUserApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user, User.class);
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(json)
                .when().redirects().follow(false)
                .post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in postUserApiCall(" + user.getUsername() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static void postUser(User user, String authUser, String authPassword) {
        log.debug("postUser(" + user.getUsername() + ")");
        Assert.assertFalse(checkIfUserExists(user.getUsername(), authUser, authPassword), "User '" + user.getUsername() + " already exists!");
        Response response = postUserApiCall(user, authUser, authPassword);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in postUser(" + user.getUsername() + ") Api Call! Response Body: " + sResponseBody);
        log.debug("User Created: " + checkIfUserExists(user.getUsername(), authUser, authPassword));
    }

    public static void postUser(User user) {
        postUser(user, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

   /////// REST API FOR HEROES ///////////////////

    private static Response checkIfHeroExistsApiCall(String heroName, String authUser, String authPassword) {

        String apiCall = BASE_URL + ApiCalls.createCheckIfHeroExistsApiCall(heroName);

        Response response = null;

        try {
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .header("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(apiCall);

        } catch (Exception e) {
            Assert.fail("Exception in checkIfHeroExists(" + heroName + ") Api Call: " + e.getMessage());
        }

        return response;

    }


    public static boolean checkIfHeroExists(String heroName, String authUser, String authPassword) {
        log.debug("checkIfHeroExists(" + heroName + ")");

        Response response = checkIfHeroExistsApiCall(heroName, authUser, authPassword);

        int status = response.getStatusCode();

        String sResponseBody = response.getBody().asString();

        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in checkIfHeroExists(" + heroName + ") Api Call! Response Body: " + sResponseBody);

        if (!(sResponseBody.equalsIgnoreCase("true") || sResponseBody.equalsIgnoreCase("false"))) {
            Assert.fail("Cannot convert response body '" + sResponseBody + "' to boolean value!");
        }

        return Boolean.parseBoolean(sResponseBody);
    }

    public static boolean checkIfHeroExists(String heroName) {
        return checkIfUserExists(heroName, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    ///////////////////////////

    private static Response getHeroApiCall(String heroName, String authUser, String authPassword) {

        String apiCall = BASE_URL + ApiCalls.createGetHeroApiCall(heroName);

        Response response = null;

        try {
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(apiCall);
        } catch (Exception e) {
            Assert.fail("Exception in getUserApiCall(" + heroName + ") Api Call: " + e.getMessage());
        }
        return response;
    }


    public static String getHeroJsonFormat(String heroName, String authUser, String authPassword) {

        log.debug("getHeroJsonFormat(" + heroName + ")");

        Assert.assertTrue(checkIfHeroExists(heroName, authUser, authPassword),
            "Hero '" + heroName + "' doesn't exist!");

        Response response = getUserApiCall(heroName, authUser, authPassword);

        int status = response.getStatusCode();

        String responseBody = response.getBody().asPrettyString();
        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in getHero(" + heroName + ") Api Call! Response Body: " + responseBody);

        return responseBody;
    }


    public static String getHeroJsonFormat(String heroName) {
        return getHeroJsonFormat(heroName, ADMIN_USERNAME, ADMIN_PASSWORD);
    }


    public static User getHero(String heroName, String authUser, String authPass) {
        log.debug("getHero(" + heroName + ")");
        String json = getHeroJsonFormat(heroName, authUser, authPass);
        Gson gson = new Gson();
        //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, User.class);
    }

    public static User getHero(String heroName) {
        return getUser(heroName, ADMIN_USERNAME, ADMIN_PASSWORD);
    }


    private static Response deleteHeroApiCall(String heroName, String sAuthUser, String sAuthPass) {
        String sApiCall = BASE_URL + ApiCalls.createDeleteHeroApiCall(heroName);
        Response response = null;
        try {
            response = RestAssured.given().auth().basic(sAuthUser, sAuthPass)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .when().redirects().follow(false)
                .delete(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in deleteHeroApiCall(" + heroName + ") Api Call: " + e.getMessage());
        }
        return response;
    }


    public static void deleteHero(String heroName, String authUser, String authPassword) {
        log.debug("deleteHero(" + heroName + ")");
        Assert.assertTrue(checkIfHeroExists(heroName, authUser, authPassword), "Hero '" + heroName + " doesn't exist!");
        Response response = deleteHeroApiCall(heroName, authUser, authPassword);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200,
            "Wrong Response Status Code in deleteHero(" + heroName + ") Api Call! Response Body: " + sResponseBody);
        log.debug("Hero Deleted: " + !checkIfHeroExists(heroName, authUser, authPassword));
    }

    public static void deleteHero(String heroName) {
        deleteHero(heroName, ADMIN_USERNAME, ADMIN_PASSWORD);
    }


    private static Response postHeroApiCall(Hero heroName, String authUser, String authPassword) {
        String sApiCall = BASE_URL + ApiCalls.createPostHeroApiCall();
        Response response = null;
        try {
            Gson gson = new Gson();
            String json = gson.toJson(heroName, Hero.class);
            response = RestAssured.given().auth().basic(authUser, authPassword)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .body(json)
                .when().redirects().follow(false)
                .post(sApiCall);
        } catch (Exception e) {
            Assert.fail("Exception in postHeroApiCall(" + heroName.getHeroName() + ") Api Call: " + e.getMessage());
        }
        return response;
    }

    public static void postHero(Hero heroName, String authUser, String authPassword) {
        log.debug("postHero(" + heroName.getHeroName() + ")");
        Assert.assertFalse(checkIfHeroExists(heroName.getHeroName(), authUser, authPassword), "Hero '" + heroName.getHeroName() + " already exists!");
        Response response = postHeroApiCall(heroName, authUser, authPassword);
        int status = response.getStatusCode();
        String sResponseBody = response.getBody().asString();
        Assert.assertEquals(status, 200, "Wrong Response Status Code in postUser(" + heroName.getHeroName() + ") Api Call! Response Body: " + sResponseBody);
        log.debug("Hero Created: " + checkIfHeroExists(heroName.getHeroName(), authUser, authPassword));
    }

    public static void postHero(Hero heroName) {
        postHero(heroName, ADMIN_USERNAME, ADMIN_PASSWORD);
    }


}
