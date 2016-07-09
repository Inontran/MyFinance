package com.vk.id194177937.myfinance.core.wrapper;

import com.vk.id194177937.myfinance.core.dao.interfaces.SourceDAO;
import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.interfaces.Source;
import com.vk.id194177937.myfinance.core.utils.TreeUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Inontran on 08.07.16.
 */
public class SourceSynchronizer implements SourceDAO {

    private SourceDAO sourceDAO;
    private TreeUtils<Source> treeUtils = new TreeUtils();// построитель дерева

    // Все коллекции хранят ссылки на одни и те же объекты, но в разных "срезах"
    // при удалении - удалять нужно из всех коллекций
    private List<Source> treeList = new ArrayList<>(); // хранит деревья объектов без разделения по типам операции
    private Map<OperationType, List<Source>> sourceMap = new EnumMap<>(OperationType.class); // деревья объектов с разделением по типам операции
    private Map<Long, Source> identityMap = new HashMap<>(); // нет деревьев, каждый объект хранится отдельно, нужно для быстрого доступа к любому объекту по id (чтобы каждый раз не использовать перебор по всей коллекции List и не обращаться к бд)


    public SourceSynchronizer(SourceDAO sourceDAO) {
        this.sourceDAO = sourceDAO;
    }

    public void init() {
        List<Source> sourceList = sourceDAO.getAll();// запрос в БД происходит только один раз, чтобы заполнить коллекцию sourceList

        for (Source s : sourceList) {
            identityMap.put(s.getId(), s);
            treeUtils.addToTree(s.getParent().getId(), s, treeList);
        }

        // важно - сначала построить деревья, уже потом разделять по типам операции
        fillSourceMap(treeList);// разделяем по типам операции


    }

    private void fillSourceMap(List<Source> list) {
        for (OperationType type : OperationType.values()) {
            // используем lambda выражение для фильтрации
            sourceMap.put(type, list.stream().filter(s -> s.getOperationType() == type).collect(Collectors.toList()));
        }

    }

    @Override
    public List<Source> getAll() {
        return treeList;
    }

    @Override
    public Source get(long id) {
        // не делаем запрос в БД, а получаем ранее загруженный объект из коллекции
        return identityMap.get(id);
    }

    @Override
    public boolean update(Source source) {
        if (sourceDAO.update(source)){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Source source) {
        // TODO добавить нужные Exceptions
        if (sourceDAO.delete(source)) {
            identityMap.remove(source.getId());
            if (source.getParent()!=null) {// если удаляем дочерний элемент
                source.getParent().remove(source);// т.к. у каждого дочернего элемента есть ссылка на родительский - можно быстро удалять элемент из дерева без поиска по всему дереву
            }else{// если удаляем элемент, у которого нет родителей
                sourceMap.get(source.getOperationType()).remove(source);
                treeList.remove(source);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean addSource(Source source) {
        //TODO реализовать добавление нового источника
//        if (sourceDAO.addSource(source)) sourceList.add(source);
        return false;
    }

    @Override
    public List<Source> getList(OperationType operationType) {
        return sourceMap.get(operationType);
    }

    // если понадобится напрямую получить объекты из БД - можно использовать sourceDAO
    public SourceDAO getSourceDAO() {
        return sourceDAO;
    }
}
