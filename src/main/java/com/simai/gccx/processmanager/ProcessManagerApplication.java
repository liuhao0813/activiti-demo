package com.simai.gccx.processmanager;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ProcessManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessManagerApplication.class, args);
    }

}
