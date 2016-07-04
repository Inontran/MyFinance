package com.vk.id194177937.myfinance.core.exceptions;

/**
 * Created by Inontran on 04.07.16.
 */
public class AmountException extends Exception {
    public AmountException() {
    }

    public AmountException(String message) {
        super(message);
    }

    public AmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmountException(Throwable cause) {
        super(cause);
    }
}
