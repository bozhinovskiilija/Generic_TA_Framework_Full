package tests.login;

import objects.User;
import org.testng.annotations.Test;
import tests.BaseTestClass;

public class DemoTest extends BaseTestClass {

    @Test
    public void testDemoTest(){
        User user = User.readUserFromCSVFile("dedoje");
        log.info(user);
    }
}
