package com.lll.amazing.flow;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Jacky Chan
 * @version 1.0.0
 * @description
 * @date 2022-07-16 18:17
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
