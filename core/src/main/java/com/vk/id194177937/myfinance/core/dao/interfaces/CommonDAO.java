package com.vk.id194177937.myfinance.core.dao.interfaces;

import java.util.List;

/**
 * Created by Inontran on 07.07.16.
 */
public interface CommonDAO<T> {
    List<T> getAll();
    T get(long id);
    boolean update(T depository);//boolean нужен чтобы удостовериться, что операция прошла успешно
    boolean delete(T depository);
}
