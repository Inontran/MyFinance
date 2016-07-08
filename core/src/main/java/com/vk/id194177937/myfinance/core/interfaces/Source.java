package com.vk.id194177937.myfinance.core.interfaces;

import com.vk.id194177937.myfinance.core.enums.OperationType;

import java.util.List;

/**
 * Created by Inontran on 06.07.16.
 */
public interface Source extends TreeNode{
    OperationType getOperationType();
}
