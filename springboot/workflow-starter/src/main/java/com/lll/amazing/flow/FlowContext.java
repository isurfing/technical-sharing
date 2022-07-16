package com.lll.amazing.flow;

import java.util.HashMap;
import java.util.Map;

/**
*@description
* context for the whole workflow
*@author Michael
*@date 2022-07-16 18:15
*@version 1.0
**/
public class FlowContext {

    private Object reqObj;
    private Object rspObj;
    private Object branchReqObj;
    private final Map<String, Object> attributes = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getReqObj() {
        return (T)reqObj;
    }

    public void setReqObj(Object reqObj) {
        this.reqObj = reqObj;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRspObj() {
        return (T)rspObj;
    }

    public void setRspObj(Object rspObj) {
        this.rspObj = rspObj;
    }

    public void setBranchReqObj(Object branchReqObj) {
        this.branchReqObj = branchReqObj;
    }

    public Object getBranchReqObj() {
        return branchReqObj;
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttr(String key) {
        return (T)attributes.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttr(Class<T> tClass) {
        return (T)attributes.get(tClass.getName());
    }

    public <T> void setAttr(String key, T t) {
        attributes.put(key, t);
    }

    private FlowContext(Object reqObj) {
        this.reqObj = reqObj;
    }

    public static FlowContext get(Object reqObj) {
        return new FlowContext(reqObj);
    }

}
