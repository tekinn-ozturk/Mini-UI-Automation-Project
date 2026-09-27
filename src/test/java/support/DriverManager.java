package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Lazily starts one Chrome session per scenario. Headless by default so it runs on
 * a Jenkins agent without a desktop; pass -Dheadless=false to watch it locally.
 */
public final class DriverManager {

    private static WebDriver driver;

    private DriverManager() {
    }

    public static WebDriver get() {
        if (driver == null) {
            start(defaultOptions());
        }
        return driver;
    }

    public static WebDriver start(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        return driver;
    }

    public static ChromeOptions defaultOptions() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
