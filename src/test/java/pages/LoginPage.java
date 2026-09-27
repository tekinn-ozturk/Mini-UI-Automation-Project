package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page object for /login. The form fields are looked up once, when the page object is
 * created, and reused afterwards.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebElement usernameInput;
    private final WebElement passwordInput;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.usernameInput = driver.findElement(By.id("username"));
        this.passwordInput = driver.findElement(By.id("password"));
    }

    public void typeUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void typePassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void submit() {
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.stalenessOf(button));
    }

    public void login(String username, String password) {
        typeUsername(username);
        typePassword(password);
        submit();
    }

    public void clickForgotPassword() {
        driver.findElement(By.linkText("Forgot your password?")).click();
    }

    public String flashMessage() {
        return driver.findElement(By.id("flash")).getText();
    }

    public String heading() {
        return driver.findElement(By.tagName("h2")).getText();
    }
}
