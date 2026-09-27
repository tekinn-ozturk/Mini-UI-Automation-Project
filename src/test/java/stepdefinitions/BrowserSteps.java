package stepdefinitions;

import io.cucumber.java.en.Given;
import org.openqa.selenium.chrome.ChromeOptions;
import support.DriverManager;

public class BrowserSteps {

    @Given("tarayıcı {string} Chrome binary'si ile başlatılır")
    public void startBrowserWithBinary(String binaryPath) {
        ChromeOptions options = DriverManager.defaultOptions();
        options.setBinary(binaryPath);
        DriverManager.start(options);
    }
}
