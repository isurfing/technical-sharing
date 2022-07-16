# How to make the business logic more organized and clear?



### 1、Let's have an intuitive feeling first

```java
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
```

Through the above code, we can clearly know what this logic is used for.

### 2、Getting Started

#### 2.1、application.yml

```yaml
workflow:
  enabled: true
```

This attribute is used to configure whether to start the workflow as below.

```java
/**
*@description 
* flow configuration
*@author Michael
*@date 2022-07-16 19:55
*@version 1.0
**/
@Configuration
@Import(NodeExecutor.class)
@ConditionalOnProperty(
        prefix = "workflow",
        name = "enabled",
        havingValue = "true"
)
public class FlowAutoConfig {
}
```

#### 2.2、Using 'WorkflowBuilder' to build the workflow

```java
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

```

#### 2.3、How to run the workflow?

It's easy to run.

```java
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
```

### 3、Log output 

We can see how many time every single method takes, so that we are able to catch the features of every single method, such as performance, memory...

```java
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===start=>Method【payTuition】starts to run...
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.testflow.CollegeFlow     : There is a student: {fee=2500, name=Miss Lee, id=student001}
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===end===>Method【payTuition】is finished, it takes 0ms

2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===start=>Method【studyChinese】starts to run...
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.testflow.CollegeFlow     : This course is Chinese, I will do my best to learn it well
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===end===>Method【studyChinese】is finished, it takes 0ms

2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===start=>Method【retrievingScore】starts to run...
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.testflow.CollegeFlow     : We receive your keyword: Never say die, never give up!
2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.flow.Task                : ===end===>Method【retrievingScore】is finished, it takes 0ms

2022-07-16 19:33:06.247  INFO 9392 --- [           main] com.lll.amazing.WorkflowMainTest         : Flow【STUDY】 is finished, the result is 'Your score is 100, you are good student.'

```

