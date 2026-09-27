package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.DriverManager;
import support.TestConfig;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class DynamicLoadingSteps {

    private static final By START_BUTTON = By.cssSelector("#start button");
    private static final By RESULT_TEXT = By.cssSelector("#finish h4");

    @Given("dinamik yükleme sayfası {int} açılır")
    public void openDynamicLoadingExample(int example) {
        DriverManager.get().get(TestConfig.baseUrl() + "/dynamic_loading/" + example);
    }

    @When("start butonuna tıklanır")
    public void clickStart() {
        DriverManager.get().findElement(START_BUTTON).click();
    }

    @Then("{string} metni {int} saniye içinde görünür olmalı")
    public void resultTextShouldAppear(String expected, int timeoutSeconds) {
        WebElement result = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(RESULT_TEXT));
        assertEquals(expected, result.getText());
    }
}
