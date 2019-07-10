package com.simai.gccx.processmanager.service;

import com.simai.gccx.processmanager.controller.TradeParam;
import com.simai.gccx.processmanager.model.Complete;
import com.simai.gccx.processmanager.model.TaskVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GccxActivitiService {


    /**
     * 部署流程文件
     *
     */
    void deployProcess(MultipartFile multipartFile) throws IOException;


    /**
     * 启动流程
     */
    void startProcess(TradeParam tradeParam);


    /**
     * 查询所有的任务
     * @return
     */
    List<TaskVO> findAllTask();


    /**
     * 处理流程
     * @param complete
     * @return
     */
    String completeProcess(Complete complete);
}
