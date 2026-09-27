Feature: Login ve dinamik içerik regresyonu

  @SEL_NO_SUCH_ELEMENT
  Scenario: Şifremi unuttum akışı başlatılır
    Given login sayfası açılır
    When şifremi unuttum linkine tıklanır

  @SEL_TIMEOUT
  Scenario: Dinamik içerik hızlı yüklenir
    Given dinamik yükleme sayfası 2 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 2 saniye içinde görünür olmalı

  @SEL_STALE_ELEMENT
  Scenario: Sayfa yenilendikten sonra giriş yapılır
    Given login sayfası açılır
    When sayfa yenilenir
    And kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then "You logged into a secure area!" mesajı görüntülenir

  @SEL_SESSION_NOT_CREATED
  Scenario: Chrome Beta ile giriş sayfası açılır
    Given tarayıcı "C:\Program Files\Google\Chrome Beta\Application\chrome.exe" Chrome binary'si ile başlatılır
    And login sayfası açılır

  @TEST_ASSERTION_FAILURE
  Scenario: Başarılı girişten sonra güvenli alan başlığı doğrulanır
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then sayfa başlığı "Secure Area Dashboard" olmalı
