Feature: Sepet indirimleri

  @CUKE_GHERKIN_PARSE_ERROR
  Scenario Outline: Kupon kodu uygulanır
    Given login sayfası açılır
    When kullanıcı adı alanına "<user>" yazılır
    And şifre alanına "<password>" yazılır
    And giriş butonuna tıklanır
    Then "<message>" mesajı görüntülenir

    Examples:
      | user     | password             | message                        |
      | tomsmith | SuperSecretPassword! | You logged into a secure area! |
      | tomsmith | wrong-password       |
      | nobody   | x                    | Your username is invalid!      |
