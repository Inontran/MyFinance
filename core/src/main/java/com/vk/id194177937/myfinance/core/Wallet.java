package com.vk.id194177937.myfinance.core;

/**
 * Created by ubuntuvod on 03.07.16.
 */
public class Wallet {
    private float balance;
    private String name;
    private String name_currency;
    private Wallet parentWallet;

    public Wallet() {
    }



    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_currency() {
        return name_currency;
    }

    public void setName_currency(String name_currency) {
        this.name_currency = name_currency;
    }

    public Wallet getParentWallet() {
        return parentWallet;
    }

    public void setParentWallet(Wallet parentWallet) {
        this.parentWallet = parentWallet;
    }
}
