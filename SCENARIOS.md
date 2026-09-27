# Build Failure Senaryoları

Bu proje, **build-analyzer**'ın parser'ını ve AI kök neden analizini test etmek için
gerçek dünyada sık görülen 20 build hatasını tekrar üretir.

- Test edilen site: https://the-internet.herokuapp.com (açık kaynak Selenium pratik sitesi)
- Senaryo seçimi: Jenkins job'unda **Build with Parameters → `SCENARIO`**
- `NONE` → yeşil build (kontrol grubu: 2 smoke senaryosu geçer)

> İlk kez parametreli Jenkinsfile ile build alındığında Jenkins parametreleri henüz tanımaz ve
> build `NONE` ile çalışır. Bir kez "Build Now" deyin, sonra "Build with Parameters" görünür.

## Senaryolar ve beklenen analiz

"Beklenen kategori" build-analyzer'ın `ErrorCategory` enum'una göredir. "Mevcut kural" sütunu,
bugünkü `ErrorClassifier` anahtar kelime kurallarının yerel Maven loguna uygulanmış halidir
(Jenkins konsolunda ek satırlar olacağı için küçük farklar olabilir).

| # | SCENARIO | Beklenen kategori | Mevcut kural | Beklenen kök neden | Konum | Beklenen aksiyon |
|---|---|---|---|---|---|---|
| 1 | `SEL_NO_SUCH_ELEMENT` | SELENIUM | SELENIUM ✅ | `NoSuchElementException`: login sayfasında "Forgot your password?" linki yok (link `/forgot_password` sayfasında, login'de değil) | `LoginPage.java:50` ← `LoginSteps.java:52` | Locator/akış yanlış: önce doğru sayfaya git veya locator'ı düzelt |
| 2 | `SEL_TIMEOUT` | SELENIUM | SELENIUM ✅ | `TimeoutException`: `#finish h4` 2 sn içinde görünmedi; sayfa ~5 sn'de yükleniyor | `DynamicLoadingSteps.java:36`, `SeleniumFailures.feature:12` | Bekleme süresini gerçekçi yap (≥10 sn) |
| 3 | `SEL_STALE_ELEMENT` | SELENIUM | SELENIUM ✅ | `StaleElementReferenceException`: `LoginPage` elementleri constructor'da cache'liyor, `sayfa yenilenir` sonrası referans bayat | `LoginPage.java:28` | Element'i her kullanımda yeniden bul (By tut, WebElement tutma) |
| 4 | `SEL_SESSION_NOT_CREATED` | SELENIUM | SELENIUM ✅ | `SessionNotCreatedException`: `no chrome binary at C:\Program Files\Google\Chrome Beta\...` — agent'ta Chrome Beta kurulu değil | `SeleniumFailures.feature:25`, `BrowserSteps.java` | Agent'a Chrome Beta kur ya da binary yolunu kaldır (kod hatası değil, ortam) |
| 5 | `TEST_ASSERTION_FAILURE` | CUCUMBER (test/assertion) | SELENIUM ⚠️ | `ComparisonFailure`: beklenen `Secure Area Dashboard`, gerçek `Secure Area` — beklenen değer yanlış (ürün hatası değil) | `LoginSteps.java:63`, `SeleniumFailures.feature:34` | Feature'daki beklenen başlığı düzelt |
| 6 | `CUKE_UNDEFINED_STEP` | CUCUMBER | SELENIUM ⚠️ | `UndefinedStepException`: `yazilir` (ı yerine i) — step tanımı `yazılır` | `UndefinedStep.feature:6` | Feature'daki yazım hatasını düzelt |
| 7 | `CUKE_AMBIGUOUS_STEP` | CUCUMBER | SELENIUM ⚠️ | `AmbiguousStepDefinitionsException`: `extraglue.ambiguous.GenericFormSteps` regex'i `LoginSteps.typeUsername` ile çakışıyor | `GenericFormSteps.java` + `LoginSteps.java` | Çakışan step'lerden birini sil / metnini ayrıştır |
| 8 | `CUKE_GHERKIN_PARSE_ERROR` | CUCUMBER | MAVEN ⚠️ | `FeatureParserException`: `(14:7) inconsistent cell count within the table` — Examples satırında `message` hücresi eksik | `broken-features/Checkout.feature:14` | Eksik hücreyi ekle |
| 9 | `CUKE_HOOK_FAILURE` | CUCUMBER (hook) / config | MAVEN ⚠️ | `@Before` hook'ta `IllegalStateException: Environment config not found on classpath: config/preprod.properties` — `-Denv=preprod` için dosya yok | `TestConfig.java` ← `Hooks.java` | `config/preprod.properties` ekle veya doğru env ile çalıştır |
| 10 | `MVN_DEPENDENCY_NOT_FOUND` | MAVEN | MAVEN ✅ | `Could not find artifact com.tekin.qa:qa-test-commons:jar:2.4.1 in central` — iç kütüphane Central'da yok | `pom.xml` (profil `mvn-dependency-not-found`) | Kütüphaneyi yayınla / şirket repo'sunu settings'e ekle / sürümü düzelt |
| 11 | `MVN_COMPILATION_ERROR` | MAVEN (derleme) | MAVEN ✅ | `cannot find symbol: loginAs(...)` — `LoginPage` API'si `login(...)` olarak değişmiş | `CheckoutSteps.java:18` | Çağrıyı yeni API'ye uyarla |
| 12 | `MVN_FORKED_VM_CRASH` | MAVEN (surefire) | MAVEN ✅ | `The forked VM terminated without properly saying goodbye ... Process Exit Code: 3` — test kodu `System.exit(3)` çağırıyor | `LegacyEnvironmentCleaner.java:19` | `System.exit` yerine exception fırlat |
| 13 | `JAVA_NULL_POINTER` | UNKNOWN / Java (test kodu) | SELENIUM ⚠️ | `NullPointerException: Cannot invoke "String.trim()" because "username" is null` — `users.admin.*` config'de yok | `LoginSteps.java:42` | `admin` kullanıcısını config'e ekle, null kontrolü yap |
| 14 | `JAVA_OUT_OF_MEMORY` | UNKNOWN / Java | MAVEN ⚠️ | `OutOfMemoryError: Java heap space` — 512 MB test verisi, fork heap'i `-Xmx64m` | `ProductCatalogCache.java:21` | Veriyi stream et / heap'i artır |
| 15 | `JAVA_RELEASE_NOT_SUPPORTED` | MAVEN / Java sürümü | MAVEN ✅ | `Fatal error compiling: error: release version 30 not supported` — agent JDK'sı (21/23) release 30'u bilmiyor | `pom.xml` (profil `java-release-not-supported`) | `maven.compiler.release`'i agent JDK'sı ile uyumlu yap |
| 16 | `INFRA_TEST_ENV_UNREACHABLE` | INFRA | SELENIUM ⚠️ | `WebDriverException: net::ERR_NAME_NOT_RESOLVED` — `staging.the-internet.invalid` çözümlenemiyor; test/ürün hatası değil | `config/staging.properties` | Ortam/DNS erişimini kontrol et, doğru `base.url` |
| 17 | `INFRA_MAVEN_REPO_UNREACHABLE` | INFRA | MAVEN ⚠️ | `maven-clean-plugin ... could not be resolved ... corp-nexus ... Bilinen böyle bir ana bilgisayar yok` (UnknownHost, **Türkçe yerelleştirilmiş**) | `ci/settings-unreachable-mirror.xml` | Nexus mirror adresini/DNS/VPN'i düzelt |
| 18 | `JENKINS_TOOL_NOT_FOUND` | JENKINS | (Jenkins'te ölçülecek) | Testler **geçer**, ardından `'allure' is not recognized as an internal or external command` — agent'ta Allure CLI yok | `Jenkinsfile:104` | Allure'u Global Tool olarak tanımla / plugin kullan |
| 19 | `JENKINS_CREDENTIALS_NOT_FOUND` | JENKINS | (Jenkins'te ölçülecek) | `Could not find credentials entry with ID 'qa-env-admin-credentials'` — Maven hiç çalışmaz | `Jenkinsfile:87` | Credential'ı Jenkins'e ekle / ID'yi düzelt |
| 20 | `JENKINS_PIPELINE_SCRIPT_ERROR` | JENKINS | (Jenkins'te ölçülecek) | `groovy.lang.MissingPropertyException: No such property: REPORT_FOLDER` — tanımsız değişken | `Jenkinsfile:77` | `REPORT_FOLDER`'ı `environment {}` / parametre olarak tanımla |

## Senaryolar nasıl tetikleniyor

| Mekanizma | Senaryolar |
|---|---|
| Cucumber tag'i (`-Dcucumber.filter.tags=@<SCENARIO>`) | 1–6, 12, 13, 14 |
| Ek glue paketi (`-Dcucumber.glue=...`) | 7 |
| Ayrı feature klasörü (`src/test/resources/broken-features`) | 8 |
| Ortam seçimi (`-Denv=preprod` / `-Denv=staging`) | 9, 16 |
| Maven profili (`-P ...`) | 10, 11, 15 |
| Surefire JVM ayarı (`-Dtest.jvm.args=-Xmx64m`) | 14 |
| Bozuk `settings.xml` + boş local repo | 17 |
| Jenkinsfile adımı | 18, 19, 20 |

Senaryoyu yerelde denemek için Jenkinsfile'daki `mavenArgsFor` argümanlarını kullanın, ör.:

```bash
mvn -B clean test "-Dcucumber.filter.tags=@SEL_TIMEOUT"
```

## Notlar

- 18. senaryo agent'ta `allure` komutu **yoksa** fail eder; kuruluysa geçer.
- 19. senaryoda Credentials Binding plugin'i kurulu değilse hata `No such DSL method 'withCredentials'` olur (yine JENKINS kategorisi).
- Tarayıcı varsayılan olarak headless çalışır; yerelde izlemek için `-Dheadless=false`.
