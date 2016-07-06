package com.vk.id194177937.myfinance.core.interfaces;

import com.vk.id194177937.myfinance.core.objects.OperationType;

import java.util.List;

/**
 * Created by Inontran on 06.07.16.
 */
public interface Source {
    String getName();// обязывает реализовать свойство name
    long getId();
    OperationType getOperationType();
}
