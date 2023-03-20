package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(publish = true,
    features = {"src/test/resources/features"},
    glue = {"stepDefs"},
    tags = "@Example2"
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {



}
