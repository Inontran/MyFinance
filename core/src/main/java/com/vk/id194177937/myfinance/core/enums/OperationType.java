package com.vk.id194177937.myfinance.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inontran on 08.07.16.
 */
public enum OperationType {
    INCOME(1), OUTCOME(2), TRANSFER(3), CONVERT(4);//нумерация id как в таблице

    private static Map<Integer, OperationType> map = new HashMap<>();

    static {
        for (OperationType oper : OperationType.values()){
            map.put(oper.getId(), oper);
        }
    }

    private Integer id;

    private OperationType(Integer id){ this.id = id;}

    public Integer getId() {
        return id;
    }

    public static OperationType getType(int id){
        return map.get(id);
    }
}
