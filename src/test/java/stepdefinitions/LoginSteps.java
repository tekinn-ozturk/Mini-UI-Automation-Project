package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import support.DriverManager;
import support.TestConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("login sayfası açılır")
    public void openLoginPage() {
        DriverManager.get().get(TestConfig.baseUrl() + "/login");
        loginPage = new LoginPage(DriverManager.get());
    }

    @When("kullanıcı adı alanına {string} yazılır")
    public void typeUsername(String username) {
        loginPage.typeUsername(username);
    }

    @When("şifre alanına {string} yazılır")
    public void typePassword(String password) {
        loginPage.typePassword(password);
    }

    @When("giriş butonuna tıklanır")
    public void clickLogin() {
        loginPage.submit();
    }

    @When("{string} kullanıcısı ile giriş yapılır")
    public void loginAs(String user) {
        String username = TestConfig.get("users." + user + ".username");
        String password = TestConfig.get("users." + user + ".password");
        loginPage.login(username.trim(), password.trim());
    }

    @When("sayfa yenilenir")
    public void refreshPage() {
        DriverManager.get().navigate().refresh();
    }

    @When("şifremi unuttum linkine tıklanır")
    public void clickForgotPassword() {
        loginPage.clickForgotPassword();
    }

    @Then("{string} mesajı görüntülenir")
    public void flashMessageIsShown(String expected) {
        String actual = loginPage.flashMessage();
        assertTrue("Flash message was: " + actual, actual.contains(expected));
    }

    @Then("sayfa başlığı {string} olmalı")
    public void headingShouldBe(String expected) {
        assertEquals("Page heading", expected, loginPage.heading());
    }
}
