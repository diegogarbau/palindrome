package com.services.impl;

import com.services.IPalindromeService;
import java.util.Optional;

public class PalindromeServiceImpl implements IPalindromeService {

  @Override
  public Optional<String> highestValuePalindrome(String number, int maxAllowedChanges) {
    return Optional.of(number + maxAllowedChanges);
  }
}
