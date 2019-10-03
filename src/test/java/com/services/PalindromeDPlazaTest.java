package com.services;

import com.services.impl.PalindromeServiceDPlaza;

public class PalindromeDPlazaTest extends PalindromeServiceTest {

    @Override
    public IPalindromeService getSut() {
        return new PalindromeServiceDPlaza();
    }
}
