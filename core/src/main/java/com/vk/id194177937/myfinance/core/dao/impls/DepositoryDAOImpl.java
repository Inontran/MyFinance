package com.vk.id194177937.myfinance.core.dao.impls;

import com.vk.id194177937.myfinance.core.dao.interfaces.DepositoryDAO;
import com.vk.id194177937.myfinance.core.database.SQLiteConnection;
import com.vk.id194177937.myfinance.core.impls.DefaultDepository;
import com.vk.id194177937.myfinance.core.interfaces.Depository;
import com.vk.id194177937.myfinance.core.utils.TreeUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Inontran on 07.07.16.
 */
public class DepositoryDAOImpl implements DepositoryDAO {

    private static final String CURRENCY_TABLE = "currency_amount";
    private static final String DEPOSITORY_TABLE = "depository";
    private List<Depository> depositoryList = new ArrayList<>();


    @Override
    public boolean addCurrency(Depository depository, Currency currency) {
        // для автоматического закрытия ресурсов
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("insert into " + CURRENCY_TABLE + "(currency_code, depository_id, amount) values(?,?,?)");) {

            stmt.setString(1, currency.getCurrencyCode());
            stmt.setLong(2, depository.getId());
            stmt.setBigDecimal(3, BigDecimal.ZERO);
            if (stmt.executeUpdate() == 1) { // если была добавлена 1 запись
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
        // TODO реализовать: вместо true, false - выбрасывать исключение и перехватывать его выше, создать соотв. типы Exception
    }

    @Override
    public boolean deleteCurrency(Depository depository, Currency currency) {
        // TODO реализовать - если есть ли операции по данной валюте - запрещать удаление
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("delete from " + CURRENCY_TABLE + " where depository_id=? and currency_code=?");) {

            stmt.setLong(1, depository.getId());
            stmt.setString(2, currency.getCurrencyCode());

            if (stmt.executeUpdate() == 1) {  // если была обновлена 1 запись
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public boolean updateAmount(Depository depository, Currency currency, BigDecimal bigDecimal) {
        try(PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("update " + CURRENCY_TABLE + " set amount=? where depository_id=? and currency_code=?")) {
            stmt.setBigDecimal(1, bigDecimal);
            stmt.setLong(2, depository.getId());
            stmt.setString(3, currency.getCurrencyCode());
            if (stmt.executeUpdate() == 1) {  // если запрос выполнился
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public Depository get(long id) {
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("select * from " + DEPOSITORY_TABLE + " where id=?");) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery();){
                DefaultDepository depository = null;
                if (rs.next()){
                    depository = new DefaultDepository();
                    depository.setId(rs.getLong("id"));
                    depository.setName(rs.getString("name"));
                    depository.setParentId(rs.getLong("parent_id"));
                }
                return depository;
            }
        } catch (SQLException e) {
            Logger.getLogger(SourceDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Depository> getAll() {
        depositoryList.clear();

        try (Statement stmt = SQLiteConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("select * from " + DEPOSITORY_TABLE)) {
            while (rs.next()) {
                DefaultDepository depository = new DefaultDepository();
                depository.setId(rs.getLong("id"));
                depository.setName(rs.getString("name"));
                depository.setParentId(rs.getLong("parent_id"));

                depositoryList.add(depository);
            }
            return depositoryList;// в итоге depositoryList должен содержать только корневые элементы
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }





    @Override
    public boolean update(Depository depository) {
        // для упрощения - у хранилища даем изменить только название, изменять parent_id нельзя (для этого можно удалить и заново создать)
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("update " + DEPOSITORY_TABLE + " set name=? where id=?");) {

            stmt.setString(1, depository.getName());
            stmt.setLong(2, depository.getId());

            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public boolean delete(Depository depository) {
        // TODO реализовать - если есть ли операции по данному хранилищу - запрещать удаление
        try (PreparedStatement stmt = SQLiteConnection.getConnection().prepareStatement("delete from " + DEPOSITORY_TABLE + " where id=?");) {
            stmt.setLong(1, depository.getId());
            if (stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(DepositoryDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
