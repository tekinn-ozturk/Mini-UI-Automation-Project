package extraglue.ambiguous;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import support.DriverManager;

/**
 * "Generic" form steps added by another team. The regex overlaps with
 * stepdefinitions.LoginSteps#typeUsername, so when both packages are on the glue path
 * Cucumber cannot decide which one to run.
 */
public class GenericFormSteps {

    @When("^kullanıcı adı alanına \"([^\"]*)\" yazılır$")
    public void typeIntoUsernameField(String value) {
        DriverManager.get().findElement(By.name("username")).sendKeys(value);
    }
}
