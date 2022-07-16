package com.lll.amazing.flow;

import lombok.Builder;

import java.util.Optional;

/**
*@description
* Process Node
*@author Michael
*@date 2022-07-16 18:14
*@version 1.0
**/
@Builder
public class ProcessNode {

    volatile ProcessNode prev;
    volatile ProcessNode next;
    private String nodeType;
    private NormalTask<FlowContext> task;
    private NormalTask<FlowContext> secondTask;
    private Workflow branch;
    private Workflow secondBranch;
    private PredicateTask<FlowContext> predicate;
    public static final String NORMAL = "NORMAL";
    public static final String NORMAL_IF = "NORMAL_IF";
    public static final String MULTIPLE_IF = "MULTIPLE_IF";
    public static final String BRANCH = "BRANCH";
    public static final String BRANCH_IF = "BRANCH_IF";
    public static final String BRANCH_MUL_IF = "BRANCH_MUL_IF";

    public String getNodeType() {
        return nodeType;
    }

    public Optional<NormalTask<FlowContext>> getTask() {
        return Optional.ofNullable(task);
    }

    public Optional<NormalTask<FlowContext>> getSecondTask() {
        return Optional.ofNullable(secondTask);
    }

    public Optional<Workflow> getBranch() {
        return Optional.ofNullable(branch);
    }

    public Optional<Workflow> getSecondBranch() {
        return Optional.ofNullable(secondBranch);
    }

    public Optional<PredicateTask<FlowContext>> getPredicate() {
        return Optional.ofNullable(predicate);
    }
}
