package com.lll.amazing.flow;

/**
*@description
* task for judging logics
*@author Michael
*@date 2022-07-16 18:21
*@version 1.0
**/
@FunctionalInterface
public interface PredicateTask<T> extends Task<T> {

    Boolean judge(T t);

    @Override
    @SuppressWarnings("unchecked")
    default <R> R selfExecute(T t) {
        return (R)this.judge(t);
    }
}
