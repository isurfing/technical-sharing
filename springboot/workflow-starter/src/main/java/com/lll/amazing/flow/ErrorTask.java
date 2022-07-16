package com.lll.amazing.flow;

/**
*@description
* task for handling exception
*@author Michael
*@date 2022-07-16 18:20
*@version 1.0
**/
@FunctionalInterface
public interface ErrorTask<T> extends Task<T> {

    void errorHandle(T t, Exception e);
}
