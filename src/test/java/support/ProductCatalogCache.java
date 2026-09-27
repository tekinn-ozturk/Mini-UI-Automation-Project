package support;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads the whole product catalog snapshot into memory as test data. Each "page" of the
 * snapshot is 1 MB, so with a small heap (-Xmx64m on the agent) this does not fit.
 */
public final class ProductCatalogCache {

    private static final int PAGE_SIZE_BYTES = 1024 * 1024;

    private static final List<byte[]> PAGES = new ArrayList<>();

    private ProductCatalogCache() {
    }

    public static int loadAll(int pageCount) {
        for (int i = 0; i < pageCount; i++) {
            PAGES.add(new byte[PAGE_SIZE_BYTES]);
        }
        return PAGES.size();
    }
}
