package com.vk.id194177937.myfinance.core.dao.interfaces;

import java.util.List;

/**
 * Created by Inontran on 07.07.16.
 */
//общие действия с БД для всех объектов
public interface CommonDAO<T> {
    List<T> getAll();
    T get(long id);
    boolean update(T element);//boolean нужен чтобы удостовериться, что операция прошла успешно
    boolean delete(T element);
    boolean add(T object);
}
