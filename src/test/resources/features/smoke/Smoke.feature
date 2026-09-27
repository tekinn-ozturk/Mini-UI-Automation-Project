Feature: Smoke - the-internet.herokuapp.com

  @smoke
  Scenario: Geçerli kullanıcı ile giriş yapılır
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazılır
    And şifre alanına "SuperSecretPassword!" yazılır
    And giriş butonuna tıklanır
    Then "You logged into a secure area!" mesajı görüntülenir
    And sayfa başlığı "Secure Area" olmalı

  @smoke
  Scenario: Dinamik yüklenen içerik görüntülenir
    Given dinamik yükleme sayfası 2 açılır
    When start butonuna tıklanır
    Then "Hello World!" metni 15 saniye içinde görünür olmalı
