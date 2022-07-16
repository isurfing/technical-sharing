package com.lll.amazing.flow;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
*@description 
* flow configuration
*@author Michael
*@date 2022-07-16 20:10
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
