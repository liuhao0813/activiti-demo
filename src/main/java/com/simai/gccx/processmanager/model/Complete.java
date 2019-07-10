package com.simai.gccx.processmanager.model;


import lombok.Data;

@Data
public class Complete {


    /**
     * 处理人  可以有很多种配置方法
     *  写死，流程变量，监听都可以
     */
    private String assigness;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 处理业务的主键
     */
    private String orderNo;

    /**
     * 审批结果
     */
    private String result;

    /**
     * 审批意见
     */
    private String reson;

}
