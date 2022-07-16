package com.lll.amazing.flow;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author Michael
 * @version 1.0
 * @description To run workflow
 * @date 2022-07-16 18:31
 **/
public class WorkflowRunner {

    public static <R> R run(String workflowName, FlowContext ctx) {
        return SpringUtil.getBean(workflowName, Workflow.class).execute(ctx);
    }
}
