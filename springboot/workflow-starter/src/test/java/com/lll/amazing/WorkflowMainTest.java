package com.lll.amazing;

import com.lll.amazing.flow.FlowContext;
import com.lll.amazing.flow.Workflow;
import com.lll.amazing.flow.WorkflowBuilder;
import com.lll.amazing.flow.WorkflowRunner;
import com.lll.amazing.testflow.CollegeFlow;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkflowMainTest {

    @Test
    public void studyFlowTest() {
        Map<String, String> reqObj = new HashMap<>();
        reqObj.put("fee", "2500");
        reqObj.put("id", "student001");
        reqObj.put("name", "Miss Lee");

        String result = WorkflowRunner.run(CollegeFlow.STUDY, FlowContext.get(reqObj));

        log.info("Flow【{}】 is finished, the result is '{}'", CollegeFlow.STUDY, result);
    }
}