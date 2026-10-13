package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
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

    /**
     * Opens a page. The practice site sometimes answers a request with 503 after 30 s (Heroku request
     * timeout). When that hits the page itself or jQuery (the dynamic loading pages need it), the page is
     * loaded again, at most 5 times. Failed styles or analytics scripts do not matter to the tests.
     */
    public static void open(String url) {
        for (int attempt = 1; attempt <= 5; attempt++) {
            get().get(url);
            Object failedRequests = ((JavascriptExecutor) driver).executeScript(
                    "return performance.getEntries()"
                            + ".filter(e => e.responseStatus >= 500 && (e.entryType === 'navigation' || e.name.includes('/jquery-1')))"
                            + ".map(e => e.name).join(', ')");
            if (String.valueOf(failedRequests).isEmpty()) {
                return;
            }
            System.out.println("[DriverManager] server error for " + failedRequests + ", reloading " + url);
        }
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
