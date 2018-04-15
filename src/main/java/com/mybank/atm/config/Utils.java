package com.mybank.atm.config;

public class Utils {
    private Utils() {
        // Hide implicit constructor
    }

    @SuppressWarnings("squid:S2639") // I know what I'm doing with the regex, thanks
    public static String maskString(String str) {
        return str.isEmpty() ? str : str.replaceAll(".", "\\*");
    }
}
