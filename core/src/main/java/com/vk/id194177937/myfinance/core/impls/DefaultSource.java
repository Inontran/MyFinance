package com.vk.id194177937.myfinance.core.impls;

import com.vk.id194177937.myfinance.core.abstracts.AbstractTreeNode;
import com.vk.id194177937.myfinance.core.interfaces.Source;
import com.vk.id194177937.myfinance.core.interfaces.TreeNode;
import com.vk.id194177937.myfinance.core.objects.OperationType;

import java.util.List;

/**
 * Created by Inontran on 06.07.16.
 */
public class DefaultSource extends AbstractTreeNode implements Source {

    private OperationType operationType;

    public DefaultSource() {
    }

    public DefaultSource(String name) {
        super(name);
    }

    public DefaultSource(String name, long id) {
        super(name, id);
    }

    public DefaultSource(List<TreeNode> children) {
        super(children);
    }

    public DefaultSource(long id, List<TreeNode> children, TreeNode parent, String name) {
        super(id, children, parent, name);
    }

    public DefaultSource(String name, long id, OperationType operationType) {
        super(name, id);
        this.operationType = operationType;
    }

    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public void add(TreeNode child) {
        //TODO применить паттерн

        //установить дочернему источнику текущий тип операции
        if (child instanceof DefaultSource) {
            ((DefaultSource) child).setOperationType(operationType);
        }
        super.add(child);
    }
}
