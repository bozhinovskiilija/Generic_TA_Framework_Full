package objects;


import com.github.javafaker.Faker;
import org.testng.Assert;
import utils.DateTimeUtils;
import utils.PropertiesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//Pojo - Plain Old Java Object
//for registering new users
public class User {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String about;
    private String secretQuestion;
    private String secretAnswer;
    private Long createdAt; //64 bits - number of milliseconds
    private Integer heroCount;
    private List<Hero> heroes;//contains no duplicate elements


    //general constructor for with all needed information(constructor that accept all parameters)
    //constructors can be private if you don't want tester to directly access them and create one public method for creating new users
    private User(final String username, final String password, final String email, final String firstName,
                final String lastName, final String about,
                final String secretQuestion, final String secretAnswer, final Date createdAt, final Integer heroCount,
                final List<Hero> heroes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        setCreatedAt(createdAt);
        this.heroCount = heroCount;
        this.heroes = heroes;
    }


    //for user that is not created in the DB (newly created user) - hardcoded
    private User(final String username, final String password, final String email, final String firstName,
                final String lastName, final String about,
                final String secretQuestion, final String secretAnswer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.createdAt = null;
        this.heroCount = 0;
        this.heroes = new ArrayList<>();
    }


    //for creating new unique user (include test data)
    private User(final String username) {
        this.username = username;
        this.password = PropertiesUtils.getDefaultPassword();
        this.email = username + "@mail.com";
        this.firstName = createRandomFirstName();
        this.lastName = createRandomLastName();
        this.about = createRandomAbout();
        this.secretQuestion = "Question?";
        this.secretAnswer = "Answer";
        this.createdAt = null;
        this.heroCount = 0;
        this.heroes = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(final String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(final String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }


    public String getAbout() {
        return about;
    }


    public void setAbout(final String about) {
        this.about = about;
    }


    public String getSecretQuestion() {
        return secretQuestion;
    }


    public void setSecretQuestion(final String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }


    public String getSecretAnswer() {
        return secretAnswer;
    }


    public void setSecretAnswer(final String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }


    public Date getCreatedAt() {

        if (this.createdAt == null) {
            return null;
        }

        return DateTimeUtils.getDateTime(this.createdAt);
    }


    public void setCreatedAt(Date date) {
        if (date == null) {
            this.createdAt = null;
        } else {
            this.createdAt = date.getTime();
        }
    }


    public Integer getHeroCount() {
        return heroCount;
    }


    public List<Hero> getHeroes() {
        return heroes;
    }


    public void setHeroes(List<Hero> heroes) {

        if (heroes == null) {
            this.heroCount = 0;
        } else {
            this.heroCount = heroes.size();
        }
    }


    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }


    public void addHero(Hero hero) {
        if (this.heroes == null) {
            heroes = new ArrayList<>();
            heroCount = 0;
        }
        if (!heroes.contains(hero)) {
            heroes.add(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail("User '" + getUsername() + "' can not have two heroes with the same name");
        }
    }


    public void removeHero(Hero hero) {

        if (heroes.contains(hero)) {
            heroes.remove(hero);
            heroCount = heroes.size();
        } else {
            Assert.fail("User '" + getUsername() + "' does not have hero with the name");
        }

    }

    public Hero getHero(String heroName){
        for(Hero h : heroes){
            if (h.getHeroName().equals(heroName)) {

                return h;
            }
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User user = (User) o;
        return username.equals(user.username);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username);
    }


    private String createRandomFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    private String createRandomLastName() {
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    private String createRandomAbout() {
        Faker faker = new Faker();
        return faker.chuckNorris().fact();
    }


    public static User createNewUniqueUser(String sUsername) {
        String username = sUsername.toLowerCase() + DateTimeUtils.getDateTimeStamp();
        if (username.length() > 35) {
            Assert.fail("Username '" + username + "' cannot be longer than 35 characters!");
        }
        return new User(username);
    }


    @Override
    public String toString() {
        return "User {"
            + "Username: " + getUsername() + ", "
            + "Password: " + getPassword() + ", "
            + "Email: " + getEmail() + ", "
            + "Name: " + getFullName() + ", "
            + "About: " + getAbout() + ", "
            + "Secret Question: " + getSecretQuestion() + ", "
            + "Secret Answer: " + getSecretAnswer() + ", "
            + "Created at: " + getCreatedAt() + ", "
            + "Hero count: " + getHeroCount() + ", "
            + "Heroes: " + getHeroes() + "}";
    }

}
