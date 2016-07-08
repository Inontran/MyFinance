package com.vk.id194177937.myfinance.core.wrapper;

import com.vk.id194177937.myfinance.core.dao.interfaces.SourceDAO;
import com.vk.id194177937.myfinance.core.interfaces.Source;

import java.util.List;

/**
 * Created by Inontran on 08.07.16.
 */
public class SourceSynchronizer implements SourceDAO {

    private SourceDAO sourceDAO;
    private List<Source> sourceList;

    public SourceSynchronizer(SourceDAO sourceDAO) {
        this.sourceDAO = sourceDAO;
    }

    @Override
    public List<Source> getAll() {
        if (sourceList==null){
            sourceList = sourceDAO.getAll();
        }
        return sourceList;
    }

    @Override
    public Source get(long id) {
        return null;
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
        if (sourceDAO.delete(source)){
            sourceList.remove(source);
            return true;
        }
        return false;
    }
}
