Feature: Test verisi ve ortam hazırlığı

  @JAVA_NULL_POINTER
  Scenario: Admin kullanıcısı ile giriş yapılır
    Given login sayfası açılır
    When "admin" kullanıcısı ile giriş yapılır
    Then "You logged into a secure area!" mesajı görüntülenir

  @JAVA_OUT_OF_MEMORY
  Scenario: Ürün kataloğu test verisi hazırlanır
    Given ürün kataloğunun 512 sayfası belleğe yüklenir
    And login sayfası açılır

  @MVN_FORKED_VM_CRASH
  Scenario: Test öncesi indirme klasörü temizlenir
    Given indirme klasörü temizlenir
    And login sayfası açılır
