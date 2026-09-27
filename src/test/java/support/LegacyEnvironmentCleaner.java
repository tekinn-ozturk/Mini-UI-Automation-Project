package support;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Old cleanup utility copied from a command-line tool. It still calls System.exit()
 * on failure, which kills the Surefire fork instead of failing a single test.
 */
public final class LegacyEnvironmentCleaner {

    private LegacyEnvironmentCleaner() {
    }

    public static void cleanDownloads() {
        Path downloads = Path.of(System.getProperty("user.home"), "qa-downloads", "locked");
        if (!Files.exists(downloads)) {
            System.err.println("[LegacyEnvironmentCleaner] download folder is not available: " + downloads);
            System.exit(3);
        }
    }
}
