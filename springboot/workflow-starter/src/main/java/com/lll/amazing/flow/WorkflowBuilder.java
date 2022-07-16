package com.lll.amazing.flow;

/**
*@description
* to build a workflow
*@author Michael
*@date 2022-07-16 18:25
*@version 1.0
**/
public class WorkflowBuilder {

    private final Workflow workflow = new Workflow();

    private WorkflowBuilder() {}

    public static WorkflowBuilder get() {
        return new WorkflowBuilder();
    }

    public WorkflowBuilder init(NormalTask<FlowContext> task) {
        this.workflow.addLast(task);
        return this;
    }

    public WorkflowBuilder process(NormalTask<FlowContext> task) {
        this.workflow.addLast(task);
        return this;
    }

    public WorkflowBuilder result(NormalTask<FlowContext> task) {
        this.workflow.addLast(task);
        return this;
    }

    public WorkflowBuilder errHandler(ErrorTask<FlowContext> errorTask) {
        this.workflow.errHandler(errorTask);
        return this;
    }

    public WorkflowBuilder doFinally(NormalTask<FlowContext> finallyTask) {
        this.workflow.doFinally(finallyTask);
        return this;
    }

    public WorkflowBuilder predicate(PredicateTask<FlowContext> predicate, NormalTask<FlowContext> task) {
        this.workflow.addLast(predicate, task);
        return this;
    }

    public WorkflowBuilder predicate(PredicateTask<FlowContext> predicate, NormalTask<FlowContext> task, NormalTask<FlowContext> task2) {
        this.workflow.addLast(predicate, task, task2);
        return this;
    }

    public WorkflowBuilder predicate(Workflow branch) {
        this.workflow.addLast(branch);
        return this;
    }

    public WorkflowBuilder predicate(PredicateTask<FlowContext> predicate, Workflow branch) {
        this.workflow.addLast(predicate, branch);
        return this;
    }

    public WorkflowBuilder predicate(PredicateTask<FlowContext> predicate, Workflow branch, Workflow branch2) {
        this.workflow.addLast(predicate, branch, branch2);
        return this;
    }

    public Workflow build() {
        return this.workflow;
    }
}
