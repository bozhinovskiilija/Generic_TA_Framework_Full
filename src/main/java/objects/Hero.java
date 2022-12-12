package objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import utils.DateTimeUtils;

import java.util.Date;
import java.util.Objects;

public class Hero {

   // @Expose  // if you want to read only certain fields from the response json
    @SerializedName("name")
    private String heroName;

    @SerializedName("type")
    private String heroClass;

    @SerializedName("level")
    private Integer heroLevel;

    //username
    private String username;
    //createdAt
    private Long createdAt;


    //First constructor that accepts all parameters
    private Hero(String heroName, String heroClass, int heroLevel, String username, Date createdAt) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(createdAt);
    }

    //Second constructor that assumes that the hero is not stored in the DB
    //but the user will create that hero
    private Hero(String heroName, String heroClass, int heroLevel, String username) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(username);
        this.setCreatedAt(null);
    }

    //We have hero before it is created into the database
    private Hero(String heroName, String heroClass, int heroLevel) {
        this.setHeroName(heroName);
        this.setHeroClass(heroClass);
        this.setHeroLevel(heroLevel);
        this.setUsername(null);
        this.setCreatedAt(null);
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass = heroClass;
    }

    public Integer getHeroLevel() {
        return heroLevel;
    }

    public void setHeroLevel(Integer heroLevel) {
        this.heroLevel = heroLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        if(createdAt == null) {
            return null;
        }
        return DateTimeUtils.getDateTime(this.createdAt);
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Date date) {
        if (date == null) {
            this.createdAt = null;
        } else {
            this.createdAt = date.getTime();
        }
    }

    public static Hero createNewUniqueHero(User user, String sHeroName, String sHeroClass, int iHeroLevel) {
        String heroName = sHeroName + DateTimeUtils.getDateTimeStamp();
        return new Hero(heroName, sHeroClass, iHeroLevel, user.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return Objects.equals(getHeroName(), hero.getHeroName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeroName());
    }

    @Override
    public String toString() {
        return "Hero {"
            + "Hero Name: " + getHeroName() + ", "
            + "Hero Class: " + getHeroClass() + ", "
            + "Hero Level: " + getHeroLevel() + ", "
            + "Username: " + getUsername() + ", "
            + "Created at: " + getCreatedAt() + "}";
    }

}
