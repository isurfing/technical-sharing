package com.lll.amazing.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
*@description 
* all tasks should extend this
*@author Michael
*@date 2022-07-16 18:13
*@version 1.0
**/
public interface Task<T> extends Serializable {

    String PRE_FIX = "Task:";

    Logger LOG = LoggerFactory.getLogger(Task.class);

    default <R> R exec(T t) {
        long startTime = System.currentTimeMillis();

        String methodName = getMethodName();
        LOG.info("===start=>Method【{}】starts to run...", methodName);

        R r = this.selfExecute(t);

        long endTime = System.currentTimeMillis();
        LOG.info("===end===>Method【{}】is finished, it takes {}ms\r\n", methodName, (endTime - startTime));

        return r;
    }

    default <R> R selfExecute(T t) {

        return null;
    }

    default String getMethodName() {
        try {
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(this);
            return serializedLambda.getImplMethodName();
        } catch (Exception e) {
            LOG.error("get method name error", e);
            return null;
        }
    }
}
