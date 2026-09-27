Feature: Hatalı giriş denemesi

  @CUKE_UNDEFINED_STEP
  Scenario: Yanlış şifre ile giriş reddedilir
    Given login sayfası açılır
    When kullanıcı adı alanına "tomsmith" yazilir
    And şifre alanına "wrong-password" yazılır
    And giriş butonuna tıklanır
    Then "Your password is invalid!" mesajı görüntülenir
