package com.vk.id194177937.myfinance.core.abstracts;

import com.vk.id194177937.myfinance.core.interfaces.TreeNode;

import java.util.ArrayList;
import java.util.List;



public abstract class AbstractTreeNode implements TreeNode{

    private long id;
    private List<TreeNode> children = new ArrayList<>();
    private TreeNode parent;
    private String name;
    private long parentId;

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public AbstractTreeNode() {
    }

    public AbstractTreeNode(String name) {
        this.name = name;
    }

    public AbstractTreeNode(List<TreeNode> children) {
        this.children = children;
    }

    public AbstractTreeNode(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public AbstractTreeNode(long id, List<TreeNode> children, TreeNode parent, String name) {
        this.id = id;
        this.children = children;
        this.parent = parent;
        this.name = name;
        this.parentId = parent.getId();
    }

    @Override
    public void add(TreeNode child) {
        child.setParent(this);
        children.add(child);
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public void remove(TreeNode child) {
        children.remove(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public TreeNode getChild(long id) {
        for (TreeNode child: children) {
            if (child.getId() == id){
                return child;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTreeNode that = (AbstractTreeNode) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasChildren(){
        return !children.isEmpty();
    }

    @Override
    public boolean hasParent() {
        return parent!=null;
    }
}
