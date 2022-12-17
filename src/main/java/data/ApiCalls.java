package data;

public class ApiCalls {

    private static final String CHECK_IF_USER_EXISTS = "/api/users/exists/";
    private static final String GET_USER = "/api/users/findByUsername/";
    private static final String POST_USER = "/api/users/add";
    private static final String DELETE_USER = "/api/users/deleteByUsername/";

    private static final String CHECK_IF_HERO_EXISTS = "/api/heroes/exists/";

    private static final String GET_HERO = "/api/heroes/findByName/";

    private static final String POST_HERO = "/api/heroes/add/";

    private static final String DELETE_HERO = "/api/heroes/deleteByName/";


    public static String createCheckIfUserExistsApiCall(String sUsername) {
        return CHECK_IF_USER_EXISTS + sUsername;
    }

    public static String createGetUserApiCall(String sUsername) {
        return GET_USER + sUsername;
    }

    public static String createPostUserApiCall() {
        return POST_USER;
    }

    public static String createDeleteUserApiCall(String sUsername) {
        return DELETE_USER + sUsername;
    }

    ////////////////// HEROES API CALS ////////////////////////////
    public static String createCheckIfHeroExistsApiCall(String heroName) {
        return CHECK_IF_HERO_EXISTS + heroName;
    }

    public static String createGetHeroApiCall(String heroName) {
        return GET_HERO + heroName;
    }

    public static String createPostHeroApiCall() {
        return POST_HERO;
    }

    public static String createDeleteHeroApiCall(String heroName) {
        return DELETE_HERO + heroName;
    }

}
