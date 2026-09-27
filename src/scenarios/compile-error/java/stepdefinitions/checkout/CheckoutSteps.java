package stepdefinitions.checkout;

import io.cucumber.java.en.Given;
import pages.LoginPage;
import support.DriverManager;
import support.TestConfig;

/**
 * Written against an older LoginPage API: loginAs(...) was renamed to login(...)
 * and the return type changed, so this class no longer compiles.
 */
public class CheckoutSteps {

    @Given("kullanıcı giriş yapmış ve sepet sayfasındadır")
    public void userIsLoggedInOnCartPage() {
        DriverManager.get().get(TestConfig.baseUrl() + "/login");
        LoginPage loginPage = new LoginPage(DriverManager.get());
        String sessionId = loginPage.loginAs("tomsmith", "SuperSecretPassword!");
        DriverManager.get().get(TestConfig.baseUrl() + "/cart?session=" + sessionId);
    }
}
