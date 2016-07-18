package com.vk.id194177937.myfinance.core.wrapper;

import com.vk.id194177937.myfinance.core.dao.interfaces.OperationDAO;
import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.impls.operations.ConvertOperation;
import com.vk.id194177937.myfinance.core.impls.operations.IncomeOperation;
import com.vk.id194177937.myfinance.core.impls.operations.OutcomeOperation;
import com.vk.id194177937.myfinance.core.impls.operations.TransferOperation;
import com.vk.id194177937.myfinance.core.interfaces.Operation;
import com.vk.id194177937.myfinance.core.interfaces.Source;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class OperationSynchronizer implements OperationDAO {

    private OperationDAO operationDAO;

    // Все коллекции хранят ссылки на одни и те же объекты, но в разных "срезах"
    // при удалении - удалять нужно из всех коллекций
    private List<Operation> operationList;
    private Map<OperationType, List<Operation>> operationMap = new EnumMap<>(OperationType.class); // деревья объектов с разделением по типам операции
    private Map<Long, Operation> identityMap = new HashMap<>(); // нет деревьев, каждый объект хранится отдельно, нужно для быстрого доступа к любому объекту по id (чтобы каждый раз не использовать перебор по всей коллекции List и не обращаться к бд)

    private SourceSynchronizer sourceSync;
    private DepositorySynchronizer depositorySync;


    public OperationSynchronizer(OperationDAO operationDAO, SourceSynchronizer sourceSync, DepositorySynchronizer depositorySync) {
        this.operationDAO = operationDAO;
        this.sourceSync = sourceSync;
        this.depositorySync = depositorySync;
        init();
    }


    public void init() {
        operationList = operationDAO.getAll();// запрос в БД происходит только один раз, чтобы заполнить коллекцию operationList

        for (Operation s : operationList) {
            identityMap.put(s.getId(), s);
        }

        // важно - сначала построить деревья, уже потом разделять по типам операции
        fillOperationMap();// разделяем по типам операции


    }

    // чтобы не дублировать этот метод в разных DAOImpl - можно его вынести
    private void fillOperationMap() {
        // в operationMap и operationList находятся одни и те же объекты!!

//        for (OperationType type : OperationType.values()) {
             //используем lambda выражение для фильтрации
//            operationMap.put(type, operationList.stream().filter(o -> o.getOperationType() == type).collect(Collectors.toList()));
//        }
        ArrayList<Operation> incomeOperation = new ArrayList<>();
        ArrayList<Operation> outcomeOperation = new ArrayList<>();
        ArrayList<Operation> transferOperation = new ArrayList<>();
        ArrayList<Operation> convertOperation = new ArrayList<>();

        // проход по коллекции только один раз
        for (Operation operation : operationList){
            switch (operation.getOperationType()){
                case INCOME:{
                    incomeOperation.add(operation);
                    break;
                }
                case OUTCOME:{
                    outcomeOperation.add(operation);
                    break;
                }
                case TRANSFER:{
                    transferOperation.add(operation);
                    break;
                }
                case CONVERT:{
                    convertOperation.add(operation);
                    break;
                }
            }
        }
        operationMap.put(OperationType.INCOME, incomeOperation);
        operationMap.put(OperationType.OUTCOME, outcomeOperation);
        operationMap.put(OperationType.TRANSFER, transferOperation);
        operationMap.put(OperationType.CONVERT, convertOperation);
    }


    @Override
    public List<Operation> getAll() {
        Collections.sort(operationList);
        return operationList;
    }

    @Override
    public Operation get(long id) {
        return identityMap.get(id);
    }

    @Override
    public boolean update(Operation operation) {
        if (delete(operationDAO.get(operation.getId())) && add(operation)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Operation operation) {
        // TODO добавить нужные Exceptions
        if (operationDAO.delete(operation) && revertBalance(operation)) {// если в БД удалилось нормально
            removeFromCollections(operation);
            return true;
        }
        return false;
    }

    private boolean revertBalance(Operation operation) {
        boolean updateAmountResult = false;

        try {

            // в зависимости от типа операции - обновляем баланс
            switch (operation.getOperationType()) {
                case INCOME: { // если был доход - значит обратно убавляем сумму

                    IncomeOperation incomeOperation = (IncomeOperation) operation;

                    BigDecimal currentAmount = incomeOperation.getToDepository().getAmount(incomeOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmount = currentAmount.subtract(incomeOperation.getFromAmount()); //прибавляем сумму операции

                    updateAmountResult = depositorySync.updateAmount(incomeOperation.getToDepository(), incomeOperation.getFromCurrency(), newAmount);

                    break;// не забываем ставить break, чтобы следующие case не выполнялись

                }
                case OUTCOME: { // если был расход - значит обратно прибавляем сумму

                    OutcomeOperation outcomeOperation = (OutcomeOperation) operation;

                    BigDecimal currentAmount = outcomeOperation.getFromDepository().getAmount(outcomeOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmount = currentAmount.add(outcomeOperation.getFromAmount()); //отнимаем сумму операции

                    updateAmountResult = depositorySync.updateAmount(outcomeOperation.getFromDepository(), outcomeOperation.getFromCurrency(), newAmount);


                    break;
                }

                case TRANSFER: { // если был трансфер - перекидываем деньги обратно с одного хранилища в другое

                    TransferOperation trasnferOperation = (TransferOperation) operation;

                    // для хранилища, откуда перевели деньги - отнимаем сумму операции
                    BigDecimal currentAmountFromStorage = trasnferOperation.getFromDepository().getAmount(trasnferOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmountFromStorage = currentAmountFromStorage.add(trasnferOperation.getFromAmount()); //отнимаем сумму операции

                    // для хранилища, куда перевели деньги - прибавляем сумму операции
                    BigDecimal currentAmountToStorage = trasnferOperation.getToDepository().getAmount(trasnferOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmountToStorage = currentAmountToStorage.subtract(trasnferOperation.getFromAmount()); //прибавляем сумму операции


                    updateAmountResult = depositorySync.updateAmount(trasnferOperation.getFromDepository(), trasnferOperation.getFromCurrency(), newAmountFromStorage) &&
                            depositorySync.updateAmount(trasnferOperation.getToDepository(), trasnferOperation.getFromCurrency(), newAmountToStorage);// для успешного результата - оба обновления должны вернуть true

                    break;

                }

                case CONVERT: { // если была конвертация - с одного хранилища отнимаем по его валюте, в другое прибавляем тоже по его валюте
                    ConvertOperation convertOperation = (ConvertOperation) operation;

                    // для хранилища, откуда перевели деньги - отнимаем сумму операции
                    BigDecimal currentAmountFromStorage = convertOperation.getFromDepository().getAmount(convertOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmountFromStorage = currentAmountFromStorage.add(convertOperation.getFromAmount()); // сколько отнимаем

                    // для хранилища, куда перевели деньги - прибавляем сумму операции
                    BigDecimal currentAmountToStorage = convertOperation.getToDepository().getAmount(convertOperation.getToCurrency());// получаем текущее значение остатка (баланса)
                    BigDecimal newAmountToStorage = currentAmountToStorage.subtract(convertOperation.getToAmount()); // сколько прибавляем


                    updateAmountResult = depositorySync.updateAmount(convertOperation.getFromDepository(), convertOperation.getFromCurrency(), newAmountFromStorage) &&
                            depositorySync.updateAmount(convertOperation.getToDepository(), convertOperation.getToCurrency(), newAmountToStorage);// для успешного результата - оба обновления должны вернуть true

                    break;
                }


            }

        } catch (CurrencyException e) {
            e.printStackTrace();
        }

        if (!updateAmountResult) {
            delete(operation);// откатываем созданную операцию
            return false;
        }

        return true;
    }

    private void removeFromCollections(Operation operation) {
        operationList.remove(operation);
        identityMap.remove(operation.getId());
        operationDAO.getList(operation.getOperationType()).remove(operation);
    }

    @Override
    // При добавлении операции – нужно сначала добавить запись в БД, затем добавить новую операцию во все коллекции и обновить баланс соотв. хранилища
    public boolean add(Operation operation) {
        if (operationDAO.add(operation)) {// если в БД добавился нормально
            addToCollections(operation);// добавляем в коллекции

            boolean updateAmountResult = false;

            try {

                // в зависимости от типа операции - обновляем баланс
                switch (operation.getOperationType()) {
                    case INCOME: { // доход

                        IncomeOperation incomeOperation = (IncomeOperation) operation;

                        BigDecimal currentAmount = incomeOperation.getToDepository().getAmount(incomeOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmount = currentAmount.add(incomeOperation.getFromAmount()); //прибавляем сумму операции

                        // обновляем баланс
                        updateAmountResult = depositorySync.updateAmount(incomeOperation.getToDepository(), incomeOperation.getFromCurrency(), newAmount);

                        break;// не забываем ставить break, чтобы следующие case не выполнялись

                    }
                    case OUTCOME: { // расход

                        OutcomeOperation outcomeOperation = (OutcomeOperation) operation;

                        BigDecimal currentAmount = outcomeOperation.getFromDepository().getAmount(outcomeOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmount = currentAmount.subtract(outcomeOperation.getFromAmount()); //отнимаем сумму операции

                        // обновляем баланс
                        updateAmountResult = depositorySync.updateAmount(outcomeOperation.getFromDepository(), outcomeOperation.getFromCurrency(), newAmount);


                        break;
                    }

                    case TRANSFER: { // перевод в одной валюте между хранилищами

                        TransferOperation trasnferOperation = (TransferOperation) operation;

                        // для хранилища, откуда перевели деньги - отнимаем сумму операции
                        BigDecimal currentAmountFromStorage = trasnferOperation.getFromDepository().getAmount(trasnferOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmountFromStorage = currentAmountFromStorage.subtract(trasnferOperation.getFromAmount()); //отнимаем сумму операции

                        // для хранилища, куда перевели деньги - прибавляем сумму операции
                        BigDecimal currentAmountToStorage = trasnferOperation.getToDepository().getAmount(trasnferOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmountToStorage = currentAmountToStorage.add(trasnferOperation.getFromAmount()); //прибавляем сумму операции

                        // обновляем баланс в обоих хранилищах
                        updateAmountResult = depositorySync.updateAmount(trasnferOperation.getFromDepository(), trasnferOperation.getFromCurrency(), newAmountFromStorage) &&
                                depositorySync.updateAmount(trasnferOperation.getToDepository(), trasnferOperation.getFromCurrency(), newAmountToStorage);// для успешного результата - оба обновления должны вернуть true

                        break;

                    }

                    case CONVERT: { // конвертация из любой валюты в любую между хранилищами
                        ConvertOperation convertOperation = (ConvertOperation) operation;

                        // для хранилища, откуда перевели деньги - отнимаем сумму операции
                        BigDecimal currentAmountFromStorage = convertOperation.getFromDepository().getAmount(convertOperation.getFromCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmountFromStorage = currentAmountFromStorage.subtract(convertOperation.getFromAmount()); // сколько отнимаем

                        // для хранилища, куда перевели деньги - прибавляем сумму операции
                        BigDecimal currentAmountToStorage = convertOperation.getToDepository().getAmount(convertOperation.getToCurrency());// получаем текущее значение остатка (баланса)
                        BigDecimal newAmountToStorage = currentAmountToStorage.add(convertOperation.getToAmount()); // сколько прибавляем


                        // обновляем баланс в обоих хранилищах
                        updateAmountResult = depositorySync.updateAmount(convertOperation.getFromDepository(), convertOperation.getFromCurrency(), newAmountFromStorage) &&
                                depositorySync.updateAmount(convertOperation.getToDepository(), convertOperation.getToCurrency(), newAmountToStorage);// для успешного результата - оба обновления должны вернуть true

                        break;
                    }


                }

            } catch (CurrencyException e) {
                e.printStackTrace();
            }

            if (!updateAmountResult) {
                delete(operation);// откатываем созданную операцию
                return false;
            }

            return true;

        }
        return false;
    }


    private void addToCollections(Operation operation) {
        operationList.add(operation);
        identityMap.put(operation.getId(), operation);
        operationDAO.getList(operation.getOperationType()).add(operation);
    }


    @Override
    public List<Operation> getList(OperationType operationType) {
        return operationMap.get(operationType);
    }


}
