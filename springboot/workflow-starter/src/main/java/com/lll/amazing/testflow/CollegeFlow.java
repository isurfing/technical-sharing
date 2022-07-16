package com.lll.amazing.testflow;

import com.lll.amazing.flow.FlowContext;
import com.lll.amazing.flow.Workflow;
import com.lll.amazing.flow.WorkflowBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
*@description
* Test: study flow
*@author Michael
*@date 2022-07-16 19:21
*@version 1.0
**/
@Slf4j
@Configuration
public class CollegeFlow {

    public static final String STUDY = "STUDY";

    private static final String MY_KEY = "MY_KEY";

    @Bean(STUDY)
    public Workflow study() {
        return WorkflowBuilder.get()
                // paying relational fee
                .init(this::payTuition)
                // attending class
                .process(this::studyChinese)
                // retrieving grade
                .result(this::retrievingScore)
                // building workflow
                .build();
    }

    private void retrievingScore(FlowContext ctx) {
        log.info("We receive your keyword: {}", (String)ctx.getAttr(MY_KEY));
        ctx.setRspObj("Your score is 100, you are good student.");
    }

    private void studyChinese(FlowContext ctx) {
        log.info("This course is Chinese, I will do my best to learn it well");
        ctx.setAttr(MY_KEY, "Never say die, never give up!");
    }

    private void payTuition(FlowContext ctx) {
        Map<String, String> studentInfo = ctx.getReqObj();
        log.info("There is a student: {}", studentInfo);
    }
}
