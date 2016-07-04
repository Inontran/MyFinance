package com.vk.id194177937.myfinance.core.exceptions;

/**
 * Created by Inontran on 04.07.16.
 */
public class CurrencyException extends Exception {
    public CurrencyException() {
    }

    public CurrencyException(String message) {
        super(message);
    }

    public CurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyException(Throwable cause) {
        super(cause);
    }
}
