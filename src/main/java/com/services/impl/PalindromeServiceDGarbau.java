package com.services.impl;

import com.services.IPalindromeService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PalindromeServiceDGarbau implements IPalindromeService {

    private static final char MAX_VALUE_CHAR = '9';
    private static final int MAX_LENGTH = 100000;

    @Override
    public Optional<String> highestValuePalindrome(final String number, final int maxAllowedChanges) {
        AtomicInteger remainingChanges = new AtomicInteger(maxAllowedChanges);
        return valueInRange(remainingChanges)
            .flatMap(validatedValue -> Optional.of(number)
                .filter(this::isNumericAndInRange)
                .flatMap(validatedNumber -> tryToMakePalindrome(validatedNumber, validatedValue))
                .map(this::reorderPalindromeWithAttendLeft));
    }

    private Optional<Tuple3> tryToMakePalindrome(final String number, final AtomicInteger maxAllowedChanges) {
        final List<Character> palindrome = number.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());
        int index = 0;
        while (number.length() / 2 > index) {
            int rightIndex;
            if (number.charAt(index) != number.charAt(rightIndex = number.length() - 1 - index)) {
                palindrome.set(index, (char) Math.max(number.charAt(index), number.charAt(rightIndex)));
                palindrome.set(rightIndex, palindrome.get(index));
                maxAllowedChanges.getAndUpdate(value -> value - 1);
            }
            index++;
        }
        return maxAllowedChanges.get() < 0 ? Optional.empty() : Optional.of(new Tuple3(number, palindrome,
            maxAllowedChanges));
    }

    private String reorderPalindromeWithAttendLeft(Tuple3 tuple) {
        int index = 0;
        int rightIndex;
        while (index <= (rightIndex = tuple.originalNumber.length() - 1 - index)) {
            if (index == rightIndex && tuple.maxAttempts.get() > 0) {
                tuple.palindrome.set(index, MAX_VALUE_CHAR);
            }
            if (tuple.palindrome.get(index) < MAX_VALUE_CHAR) {
                if (tuple.maxAttempts.get() >= 2
                    && tuple.palindrome.get(index) == tuple.originalNumber.charAt(index)
                    && tuple.palindrome.get(rightIndex) == tuple.originalNumber.charAt(rightIndex)) {
                    tuple.maxAttempts.getAndUpdate(value -> value - 2);
                    setMaxValue(tuple, index, rightIndex);
                } else if (tuple.maxAttempts.get() >= 1
                    && (tuple.palindrome.get(index) != tuple.originalNumber.charAt(index)
                    || tuple.palindrome.get(rightIndex) != tuple.originalNumber.charAt(rightIndex))) {
                    tuple.maxAttempts.getAndUpdate(value -> value - 1);
                    setMaxValue(tuple, index, rightIndex);
                }
            }
            index++;
        }
        return IntStream.rangeClosed(0, tuple.originalNumber.length() - 1)
            .mapToObj(tuple.palindrome::get)
            .map(String::valueOf)
            .collect(Collectors.joining(""));
    }

    private void setMaxValue(Tuple3 tuple, int index, int rightIndex) {
        tuple.palindrome.set(index, MAX_VALUE_CHAR);
        tuple.palindrome.set(rightIndex, MAX_VALUE_CHAR);
    }

    private boolean isNumericAndInRange(final String str) {
        return ((str != null) && (str.length() <= MAX_LENGTH)) && str.chars()
            .mapToObj(c -> (char) c)
            .allMatch(Character::isDigit);
    }

    private Optional<AtomicInteger> valueInRange(final AtomicInteger value) {
        return 0 > value.get() || value.get() > MAX_LENGTH
            ? Optional.empty()
            : Optional.of(value);
    }

    static class Tuple3 {

        final String originalNumber;
        final List<Character> palindrome;
        AtomicInteger maxAttempts;

        private Tuple3(final String originalNumber, final List<Character> palindrome, AtomicInteger maxAttempts) {
            this.originalNumber = originalNumber;
            this.palindrome = palindrome;
            this.maxAttempts = maxAttempts;
        }
    }
}
