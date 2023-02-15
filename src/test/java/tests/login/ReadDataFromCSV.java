package tests.login;

import annotations.Jira;
import objects.User;
import org.testng.annotations.Test;
import tests.BaseTestClass;

import static data.Groups.HERO;
import static data.Groups.REGRESSION;
import static data.Groups.SANITY;

@Jira(jiraID = "JIRA00054")
@Test(groups = {REGRESSION})
public class ReadDataFromCSV extends BaseTestClass {

    @Test
    public void testReadDataFromCSVFile(){
        User user = User.readUserFromCSVFile("dedoje");
        log.info(user);
    }
}
