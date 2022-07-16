package com.lll.amazing.flow;

/**
*@description 
* task for processing business
*@author Michael
*@date 2022-07-16 18:23
*@version 1.0
**/
@FunctionalInterface
public interface NormalTask<T> extends Task<T> {

    void process(T t);

    @Override
    default <R> R selfExecute(T t) {
        this.process(t);
        return null;
    }
}
