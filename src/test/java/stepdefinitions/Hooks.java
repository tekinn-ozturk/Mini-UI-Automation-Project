package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import support.DriverManager;
import support.TestConfig;

public class Hooks {

    @Before(order = 0)
    public void loadEnvironmentConfig() {
        TestConfig.load();
    }

    @After
    public void closeBrowser() {
        DriverManager.quit();
    }
}
