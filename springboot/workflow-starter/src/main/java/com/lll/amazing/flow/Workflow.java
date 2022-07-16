package com.lll.amazing.flow;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
*@description
*Workflow struct
*@author Michael
*@date 2022-07-16 18:12
*@version 1.0
**/
@Slf4j
public class Workflow {

    private final ProcessNode head = ProcessNode.builder().build();
    private final ProcessNode tail = ProcessNode.builder().build();
    private NormalTask<FlowContext> doFinally = null;
    private ErrorTask<FlowContext> errHandler = null;

    protected Workflow() {
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    private void addLast0(ProcessNode insertNode) {
        ProcessNode oldPre = this.tail.prev;
        oldPre.next = insertNode;
        insertNode.next = this.tail;
        insertNode.prev = oldPre;
        this.tail.prev = insertNode;
    }

    public void addLast(NormalTask<FlowContext> task) {
        this.addLast0(ProcessNode.builder().task(task).nodeType(ProcessNode.NORMAL).build());
    }
    public void addLast(PredicateTask<FlowContext> predicate, NormalTask<FlowContext> task) {
        this.addLast0(ProcessNode.builder().predicate(predicate).task(task).nodeType(ProcessNode.NORMAL_IF).build());
    }
    public void addLast(PredicateTask<FlowContext> predicate, NormalTask<FlowContext> task, NormalTask<FlowContext> secondTask) {
        this.addLast0(ProcessNode.builder().predicate(predicate).task(task).secondTask(secondTask).nodeType(ProcessNode.MULTIPLE_IF).build());
    }
    public void addLast(Workflow branch) {
        this.addLast0(ProcessNode.builder().branch(branch).nodeType(ProcessNode.BRANCH).build());
    }
    public void addLast(PredicateTask<FlowContext> predicate, Workflow branch) {
        this.addLast0(ProcessNode.builder().predicate(predicate).branch(branch).nodeType(ProcessNode.BRANCH_IF).build());
    }
    public void addLast(PredicateTask<FlowContext> predicate, Workflow branch, Workflow secondBranch) {
        this.addLast0(ProcessNode.builder().predicate(predicate).branch(branch).secondBranch(secondBranch).nodeType(ProcessNode.BRANCH_MUL_IF).build());
    }

    public void errHandler(ErrorTask<FlowContext> errHandler) {
        this.errHandler = errHandler;
    }

    public void doFinally(NormalTask<FlowContext> doFinally) {
        this.doFinally = doFinally;
    }

    <T> T execute(FlowContext ctx) {
        try{
            ProcessNode head = this.head;
            while(head.next != this.tail) {
                head = head.next;
                // doing each node logics
                NodeExecutor.process(ctx, head);
            }
        } catch (Exception e) {
            if(this.errHandler == null) {
                throw e;
            }
            this.errHandler.errorHandle(ctx, e);
        } finally {
            Optional.ofNullable(doFinally).ifPresent(e -> e.exec(ctx));
        }

        return ctx.getRspObj();
    }
}
