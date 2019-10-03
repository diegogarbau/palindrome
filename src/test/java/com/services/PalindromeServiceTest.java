package com.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public abstract class PalindromeServiceTest {

  public static final Duration TEST_TIMEOUT = Duration.of(100, ChronoUnit.MILLIS);

  public abstract IPalindromeService getSut();

  @ParameterizedTest(name = "{index} highest palindrome for {0} with {1} changes must be {2}")
  @CsvSource({
      "1, 3943, 3993",
      "3, 092282, 992299",
      "2, 9227, 9229",
      "2, 07382, 28382",
      "1, 999979999, 999999999",
      "3, 77777, 97979",
      "5, 12345, 99999",
      "0, 2772, 2772",
      "0, 27172, 27172",
      "1, 1889889, 9889889",
      "3, 241162, 961169",
      "3, 22, 99",
      "10, 1234567890, 9999999999",
      "1, 7, 9",
      "100, 111111111111111111222222222222222233333333333333333335555555555555555555555, 999999999999999999999999999999999999999999999999999999999999999999999999999",
      "80, 111111111111111112222222222222222333333333333333333355555555555555555555556666666, 999999999999999999999999999999999999999939999999999999999999999999999999999999999",
      "2, 999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999, 999999999991111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111199999999999",
      "3, 999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999, 999999999991111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111199999999999",
      "99, 999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999, 999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999919999999999999999999919999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
      "100, 999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999, 999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
      "200, 999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999, 999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999911111111119999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
      "8, 128392759430124, 929394959493929",
      "1, 666, 696",
      "2, 666, 969",
      "1, 721, 727",
      "2, 7239, 9339"
  })
  void highestNumberPalindromesKnown(int changes, String input, String expected) {
    final String actual = Assertions.assertTimeoutPreemptively(TEST_TIMEOUT,
        () -> getSut().highestValuePalindrome(input, changes).get());
    assertEquals(expected, actual);
  }

  @Test
  public void notPalindromeAvailableTest() {
    //Given
    String number = "0011";
    int maxAllowedChanges = 1;

    //When
    Optional<String> highestValuePalindrome = getSut().highestValuePalindrome(number, maxAllowedChanges);

    //Then
    assertFalse(highestValuePalindrome.isPresent());
  }
}