package com.vk.id194177937.myfinance.core.dao.interfaces;

import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.interfaces.Source;

import java.util.List;

/**
 * Created by Inontran on 08.07.16.
 */
public interface SourceDAO extends CommonDAO<Source>{
    //boolean нужен чтобы удостовериться, что операция прошла успешно
    boolean addSource(Source source);
    List<Source> getList(OperationType operationType);//получить список корневых элементов деревьев
}
