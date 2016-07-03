package com.vk.id194177937.myfinance.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ubuntuvod on 03.07.16.
 */
public class MyDepository {

    private String name;
    private HashMap<String, Float> mapCurrency = new HashMap<>();
    private MyDepository parentMyDepository;


    //working with currency
    //add new currency to the depository
    public void addValuta(String newCurrency)
    {
        boolean flagExisting = false;
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            if (newCurrency.equals(pair.getKey()))
            {
                System.out.println("This valuta already exist.");
                flagExisting = true;
                break;
            }
        }
        if (!flagExisting)
        {
            mapCurrency.put(newCurrency, new Float(0));
        }
    }

    //delete currency from depository
    public void deleteCurrency(String currency)
    {
        boolean flagExisting = false;
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            if (currency.equals(pair.getKey()))
            {
                mapCurrency.remove(currency);
                flagExisting = true;
                break;
            }
        }
        if (!flagExisting)
        {
            System.out.println("currency not found!");
        }
    }

    public Map.Entry<String, Float> getCurrency(String currency)
    {
        boolean flagExisting = false;
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            if (currency.equals(pair.getKey()))
            {
                return pair;
            }
        }
        if (!flagExisting)
        {
            System.out.println("currency not found!");
        }
        return null;
    }

    public List<String> getListCurrency()
    {
        List<String> listCurrency = new LinkedList<>();
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            listCurrency.add(pair.getKey());
        }
        return listCurrency;
    }

    public HashMap<String, Float> getMapCurrency() {
        return mapCurrency;
    }


    //working with balance
    public void addSum(String currency, Float sum)
    {
        boolean flagExisting = false;
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            if (currency.equals(pair.getKey()))
            {
                Float balance = pair.getValue();
                mapCurrency.put(currency, balance + sum);
                flagExisting = true;
                break;
            }
        }
        if (!flagExisting)
        {
            System.out.println("currency not found!");
        }
    }

    public void decreaseSum(String currency, Float sum)
    {
        boolean flagExisting = false;
        for (Map.Entry<String, Float> pair : mapCurrency.entrySet())
        {
            if (currency.equals(pair.getKey()))
            {
                Float balance = pair.getValue();
                mapCurrency.put(currency, balance - sum);
                flagExisting = true;
                break;
            }
        }
        if (!flagExisting)
        {
            System.out.println("currency not found!");
        }
    }








    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyDepository getParentMyDepository() {
        return parentMyDepository;
    }

    public void setParentMyDepository(MyDepository parentMyDepository) {
        this.parentMyDepository = parentMyDepository;
    }


}
