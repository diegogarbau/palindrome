package com.services;

import com.services.impl.PalindromeServiceDGarbau;

public class PalindromeDGarbauTest extends PalindromeServiceTest {

    @Override
    public IPalindromeService getSut() {
        return new PalindromeServiceDGarbau();
    }
}
