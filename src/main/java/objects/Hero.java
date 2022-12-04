package objects;

import utils.DateTimeUtils;

import java.util.Date;

public class Hero {

    //name
    private String heroName;
    //type
    private String heroClass;
    //level
    private Integer heroLevel;
    //username
    private String username;
    //createdAt
    private Long createdAt;


    public String getHeroName() {
        return heroName;
    }


    public void setHeroName(final String heroName) {
        this.heroName = heroName;
    }


    public String getHeroClass() {
        return heroClass;
    }


    public void setHeroClass(final String heroClass) {
        this.heroClass = heroClass;
    }


    public Integer getHeroLevel() {
        return heroLevel;
    }


    public void setHeroLevel(final Integer heroLevel) {
        this.heroLevel = heroLevel;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(final String username) {
        this.username = username;
    }


    public Date getCreatedAt() {

        if(this.createdAt==null){
            return null;
        }

        return DateTimeUtils.getDateTime(this.createdAt);
    }


    public void setCreatedAt(Date date) {
        if(date == null){
            this.createdAt=null;
        }else{
            this.createdAt=date.getTime();
        }
    }

}
