package com.lll.amazing.flow;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;

/**
*@description 
* To inject kinds of task nodes
*@author Michael
*@date 2022-07-16 18:23
*@version 1.0
**/
public class NodeExecutor {

    /**
     * inject 'NORMAL' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.NORMAL)
    public NodeDriven normalNode() {
        return (ctx, node) -> node.getTask().ifPresent(e -> e.exec(ctx));
    }

    /**
     * inject 'NORMAL_IF' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.NORMAL_IF)
    public NodeDriven normalIfNode() {
        return (ctx, node) -> node.getPredicate().ifPresent(p -> {
            if(p.exec(ctx)) {
                node.getTask().ifPresent(e -> e.exec(ctx));
            }
        });
    }

    /**
     * inject 'MULTIPLE_IF' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.MULTIPLE_IF)
    public NodeDriven multipleIfNode() {
        return (ctx, node) -> node.getPredicate().ifPresent(p -> {
            if(p.exec(ctx)) {
                node.getTask().ifPresent(e -> e.exec(ctx));
            } else {
                node.getSecondTask().ifPresent(e -> e.exec(ctx));
            }
        });
    }

    /**
     * inject 'MULTIPLE_IF' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.BRANCH)
    public NodeDriven branchNode() {
        return (ctx, node) -> processingBranchNode(ctx, node.getBranch().isPresent() ? node.getBranch().get() : null);
    }

    /**
     * inject 'BRANCH_IF' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.BRANCH_IF)
    public NodeDriven branchIfNode() {
        return (ctx, node) -> node.getPredicate().ifPresent(p -> {
            if(p.exec(ctx)) {
                processingBranchNode(ctx, node.getBranch().isPresent() ? node.getBranch().get() : null);
            }
        });
    }

    /**
     * inject 'BRANCH_MUL_IF' task node
     */
    @Bean(Task.PRE_FIX + ProcessNode.BRANCH_MUL_IF)
    public NodeDriven branchMulIfNode() {
        return (ctx, node) -> node.getPredicate().ifPresent(p -> {
            if(p.exec(ctx)) {
                processingBranchNode(ctx, node.getBranch().isPresent() ? node.getBranch().get() : null);
            } else {
                processingBranchNode(ctx, node.getSecondBranch().isPresent() ? node.getSecondBranch().get() : null);
            }
        });
    }

    private void processingBranchNode(FlowContext ctx, Workflow branch) {
        if(branch != null) {
            Object reqObj = ctx.getReqObj();
            // to set reqObj for new branch
            if(ctx.getBranchReqObj() != null) {
                ctx.setReqObj(ctx.getBranchReqObj());
            }
            // doing branch logics
            branch.execute(ctx);
            // reset original reqObj
            ctx.setReqObj(reqObj);
        }
    }


    @FunctionalInterface
    interface NodeDriven {
        void execute(FlowContext ctx, ProcessNode node);
    }

    public static void process(FlowContext ctx, ProcessNode node) {
        SpringUtil.getBean(Task.PRE_FIX + node.getNodeType(), NodeDriven.class).execute(ctx, node);
    }
}
