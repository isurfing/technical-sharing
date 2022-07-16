package com.lll.amazing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
*@description 
*Workflow driver
*@author Michael
*@date 2022-07-16 18:12
*@version 1.0
**/
@SpringBootApplication
(scanBasePackages = "com.lll.amazing")
public class WorkflowMain {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowMain.class, args);
    }
}
