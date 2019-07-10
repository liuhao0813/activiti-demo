package com.simai.gccx.processmanager.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskVO {

    private String owner;
    private String assignee;
    private String name;
    private Integer priority;
    private Date createTime;

    private String execution;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private String id;
}
