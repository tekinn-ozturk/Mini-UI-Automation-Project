# 15 senaryo tek build'de: 5'i geçer, 10'u 7 farklı hata grubuyla düşer. Beklenen sonuç: SCENARIOS.md → "Çoklu hata seti"
@MULTI_FAILURE_SET
Feature: Regresyon - login ve dinamik içerik

  Scenario: Geçerli kullanıcı ile giriş yapılır
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then "You logged into a secure area!" mesajı görüntülenir
    And sayfa başlığı "Secure Area" olmalı

  Scenario: Şifremi unuttum akışı başlatılır
    Given login sayfası açılır
    When şifremi unuttum linkine tıklanır

  Scenario: Yanlış şifre ile giriş reddedilir
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "wrong-password" yazılır
    And giriş butonuna tıklanır
    Then "Your password is invalid!" mesajı görüntülenir

  Scenario: Dinamik içerik hızlı yüklenir
    Given dinamik yükleme sayfası 2 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 2 saniye içinde görünür olmalı

  Scenario: Sayfa yenilendikten sonra giriş yapılır
    Given login sayfası açılır
    When sayfa yenilenir
    And kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then "You logged into a secure area!" mesajı görüntülenir

  Scenario: Hatalı girişten sonra şifre sıfırlama linkine gidilir
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "wrong-password" yazılır
    And giriş butonuna tıklanır
    And şifremi unuttum linkine tıklanır

  Scenario: Dinamik yüklenen içerik görüntülenir
    Given dinamik yükleme sayfası 2 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 15 saniye içinde görünür olmalı

  Scenario: Admin kullanıcısı ile giriş yapılır
    Given login sayfası açılır
    When "admin" kullanıcısı ile giriş yapılır
    Then "You logged into a secure area!" mesajı görüntülenir

  Scenario: Başarılı girişten sonra güvenli alan başlığı doğrulanır
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then sayfa başlığı "Secure Area Dashboard" olmalı

  Scenario: Config'teki kullanıcı ile giriş yapılır
    Given login sayfası açılır
    When "tomsmith" kullanıcısı ile giriş yapılır
    Then "You logged into a secure area!" mesajı görüntülenir

  Scenario: Gizli öğe hızlı görünür
    Given dinamik yükleme sayfası 1 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 2 saniye içinde görünür olmalı

  Scenario: Yeni dinamik yükleme örneği başlatılır
    Given dinamik yükleme sayfası 3 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 15 saniye içinde görünür olmalı

  Scenario: Gizli öğe yüklendikten sonra görünür
    Given dinamik yükleme sayfası 1 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 15 saniye içinde görünür olmalı

  Scenario: Başarılı girişten sonra şifre sıfırlama linkine gidilir
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    And şifremi unuttum linkine tıklanır

  Scenario: Çıkış yaptıktan sonra login sayfasına dönülür
    Given login sayfası açılır
    When "tomsmith" kullanıcısı ile giriş yapılır
    And çıkış butonuna tıklanır
    Then "You logged out of the secure area!" mesajı görüntülenir
