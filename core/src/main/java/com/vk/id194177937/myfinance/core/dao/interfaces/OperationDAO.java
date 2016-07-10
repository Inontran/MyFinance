package com.vk.id194177937.myfinance.core.dao.interfaces;

import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.interfaces.Operation;

import java.util.List;

public interface OperationDAO extends CommonDAO<Operation> {

    boolean add(Operation operation);

    List<Operation> getList(OperationType operationType);// получить список операций определенного типа

}
