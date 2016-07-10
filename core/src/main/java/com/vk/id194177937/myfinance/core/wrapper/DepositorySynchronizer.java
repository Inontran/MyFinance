package com.vk.id194177937.myfinance.core.wrapper;

import com.vk.id194177937.myfinance.core.dao.interfaces.DepositoryDAO;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.interfaces.Depository;
import com.vk.id194177937.myfinance.core.utils.TreeUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Inontran on 08.07.16.
 */
// синхронизирует все действия между объектами коллекции и базой данных
// паттерн Декоратор или Обертка(измененный)
public class DepositorySynchronizer implements DepositoryDAO {
    private DepositoryDAO depositoryDAO;
    // Все коллекции хранят ссылки на одни и те же объекты, но в разных "срезах"
    // при удалении - удалять нужно из всех коллекций
    private List<Depository> treeList = new ArrayList<>(); // хранит деревья объектов без разделения по типам операции
    private Map<Long, Depository> identityMap = new HashMap<>(); // нет деревьев, каждый объект хранится отдельно, нужно для быстрого доступа к любому объекту по id (чтобы каждый раз не использовать перебор по всей коллекции List и не обращаться к бд)

    private TreeUtils<Depository> treeUtils = new TreeUtils();

    public DepositorySynchronizer(DepositoryDAO depositoryDAO) {
        this.depositoryDAO = depositoryDAO;
        init();
    }

    private void init() {
        List<Depository> depositoryList = depositoryDAO.getAll();
        for (Depository depository : depositoryList){
            identityMap.put(depository.getId(), depository);
            treeUtils.addToTree(depository.getParentId(), depository, treeList);
        }
    }

    // добавляет объект во все коллекции
    private void addToCollections(Depository depository) {
        identityMap.put(depository.getId(), depository);

        if (depository.hasParent()) {
            if (!depository.getParent().getChildren().contains(depository)) {// если ранее не был добавлен уже
                depository.getParent().add(depository);
            }
        } else {// если добавляем элемент, у которого нет родителей (корневой)
            treeList.add(depository);
        }
    }




    // удаляет объект из всех коллекций
    private void removeFromCollections(Depository depository) {
        identityMap.remove(depository.getId());
        if (depository.getParent() != null) {// если удаляем дочерний элемент
            depository.getParent().remove(depository);// т.к. у каждого дочернего элемента есть ссылка на родительский - можно быстро удалять элемент из дерева без поиска по всему дереву
        } else {// если удаляем элемент, у которого нет родителей
            treeList.remove(depository);
        }
    }

    @Override
    public boolean add(Depository depository) {

        if (depositoryDAO.add(depository)) {// если в БД добавилось нормально
            addToCollections(depository);
            return true;
        }else{// откатываем добавление
            // для отката можно использовать паттерн Command (для функции Undo)
        }

        return false;
    }

    @Override
    public boolean addCurrency(Depository depository, Currency currency, BigDecimal amount) throws CurrencyException {
        if (depositoryDAO.addCurrency(depository, currency, depository.getAmount(currency))){
            depository.addCurrency(currency, depository.getAmount(currency));
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCurrency(Depository depository, Currency currency) throws CurrencyException {
        if (depositoryDAO.deleteCurrency(depository, currency)){
            depository.deleteCurrency(currency);
            return true;
        }
        return false;
    }

    // TODO при обновлении происходит наоборот - сначала обновляется объект в коллекции, потом уже в БД - продумать, как сделать, чтобы можно было откатить изменения в случае ошибки при запросе к БД
    @Override
    public boolean updateAmount(Depository depository, Currency currency, BigDecimal bigDecimal) {
        if (depositoryDAO.updateAmount(depository, currency, bigDecimal)){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Depository depository) {
        if (depositoryDAO.update(depository)){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Depository depository) {
        // TODO добавить нужные Exceptions
        if (depositoryDAO.delete(depository)){
            identityMap.remove(depository);
            if (depository.getParent()!=null)
            {
                depository.getParent().remove(depository);
            }else treeList.remove(depository);

            return true;
        }
        return false;
    }


    @Override
    public List<Depository> getAll() {
        return treeList;
    }

    @Override
    public Depository get(long id) {
        return identityMap.get(id);
    }

    public Map<Long, Depository> getIdentityMap() {
        return identityMap;
    }

    public void setIdentityMap(Map<Long, Depository> identityMap) {
        this.identityMap = identityMap;
    }

    public DepositoryDAO getDepositoryDAO() {
        return depositoryDAO;
    }

    public void setDepositoryDAO(DepositoryDAO depositoryDAO) {
        this.depositoryDAO = depositoryDAO;
    }
}
