package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        monochrome = true,
        plugin = {
                "pretty",
                "json:target/reports/cucumber.json",
                "html:target/reports/cucumber-report.html"
        }
)
public class TestRunner {

}