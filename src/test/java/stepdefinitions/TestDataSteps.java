package stepdefinitions;

import io.cucumber.java.en.Given;
import support.LegacyEnvironmentCleaner;
import support.ProductCatalogCache;

public class TestDataSteps {

    @Given("ürün kataloğunun {int} sayfası belleğe yüklenir")
    public void loadProductCatalog(int pageCount) {
        int loaded = ProductCatalogCache.loadAll(pageCount);
        System.out.println("Loaded catalog pages: " + loaded);
    }

    @Given("indirme klasörü temizlenir")
    public void cleanDownloadFolder() {
        LegacyEnvironmentCleaner.cleanDownloads();
    }
}
