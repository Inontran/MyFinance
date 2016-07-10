package com.vk.id194177937.myfinance.core.interfaces;

import com.vk.id194177937.myfinance.core.enums.OperationType;

import java.util.Calendar;

public interface Operation extends Comparable<Operation>{

    long getId();

    void setId(long id);

    OperationType getOperationType();

    Calendar getDateTime();

    String getDescription();
}
