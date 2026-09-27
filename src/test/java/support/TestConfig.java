package support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Environment configuration, loaded from classpath:config/&lt;env&gt;.properties.
 * The environment is chosen with -Denv=... (default: local).
 */
public final class TestConfig {

    private static Properties properties;

    private TestConfig() {
    }

    public static void load() {
        String env = System.getProperty("env", "local");
        String resource = "config/" + env + ".properties";
        try (InputStream in = TestConfig.class.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Environment config not found on classpath: " + resource
                        + " (env=" + env + ")");
            }
            Properties loaded = new Properties();
            loaded.load(in);
            properties = loaded;
        } catch (IOException e) {
            throw new IllegalStateException("Could not read environment config: " + resource, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String baseUrl() {
        return get("base.url");
    }
}
