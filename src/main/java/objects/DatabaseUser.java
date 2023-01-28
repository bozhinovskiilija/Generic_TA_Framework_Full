package objects;

import java.util.Date;

public class DatabaseUser {

    private String user_id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private Boolean enabled;
    private String about;
    private String secret_question;
    private String secret_answer;
    private Date created;


    public String getUser_id() {
        return user_id;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getFirst_name() {
        return first_name;
    }


    public String getLast_name() {
        return last_name;
    }


    public String getEmail() {
        return email;
    }


    public Boolean getEnabled() {
        return enabled;
    }


    public String getAbout() {
        return about;
    }


    public String getSecret_question() {
        return secret_question;
    }


    public String getSecret_answer() {
        return secret_answer;
    }


    public Date getCreated() {
        return created;
    }


    @Override
    public String toString() {
        return "DatabaseUser{" +
            "user_id='" + user_id + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", first_name='" + first_name + '\'' +
            ", last_name='" + last_name + '\'' +
            ", email='" + email + '\'' +
            ", enabled=" + enabled +
            ", about='" + about + '\'' +
            ", secret_question='" + secret_question + '\'' +
            ", secret_answer='" + secret_answer + '\'' +
            ", created=" + created +
            '}';
    }


    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }


    public void setUsername(final String username) {
        this.username = username;
    }


    public void setPassword(final String password) {
        this.password = password;
    }


    public void setFirst_name(final String first_name) {
        this.first_name = first_name;
    }


    public void setLast_name(final String last_name) {
        this.last_name = last_name;
    }


    public void setEmail(final String email) {
        this.email = email;
    }


    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }


    public void setAbout(final String about) {
        this.about = about;
    }


    public void setSecret_question(final String secret_question) {
        this.secret_question = secret_question;
    }


    public void setSecret_answer(final String secret_answer) {
        this.secret_answer = secret_answer;
    }


    public void setCreated(final Date created) {
        this.created = created;
    }

}
