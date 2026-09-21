package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExampleSteps {


    private WebDriver driver;

    @Given("Google'a git.")
    public void userOpensGoogle() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.get("https://www.google.com");
    }

    @When("Arama kutusuna tıkla.")
    public void clickSearchBox() {

        driver.findElement(By.name("qqqqqqqq")).click();
    }


}

