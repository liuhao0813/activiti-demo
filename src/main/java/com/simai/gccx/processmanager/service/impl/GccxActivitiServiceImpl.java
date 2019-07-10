package com.simai.gccx.processmanager.service.impl;

import com.simai.gccx.processmanager.controller.TradeParam;
import com.simai.gccx.processmanager.model.Complete;
import com.simai.gccx.processmanager.model.TaskVO;
import com.simai.gccx.processmanager.model.Trade;
import com.simai.gccx.processmanager.respository.TradeRespository;
import com.simai.gccx.processmanager.service.GccxActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;


@Service
@Slf4j
public class GccxActivitiServiceImpl implements GccxActivitiService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TradeRespository tradeRespository;


    @Override
    public void deployProcess(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(fileName);
        if ("zip".equalsIgnoreCase(extension) || "bar".equalsIgnoreCase(extension)) {
            ZipInputStream zip = new ZipInputStream(multipartFile.getInputStream());
            repositoryService.createDeployment().addZipInputStream(zip).deploy();
        } else if (fileName.indexOf("bpmn20.xml") != -1) {
            repositoryService.createDeployment().addInputStream(fileName, multipartFile.getInputStream()).deploy();
        } else if ("bpmn".equalsIgnoreCase(extension)) {
            repositoryService.createDeployment().addInputStream(fileName, multipartFile.getInputStream()).deploy();
        } else {
            throw new RuntimeException("文件类型不正确，无法部署流程文件");
        }

    }


    @Override
    public void startProcess(TradeParam tradeParam) {

        Trade trade = new Trade();
        trade.setOrderNo(tradeParam.getOrderNo());
        trade.setAmount(tradeParam.getAmount());
        tradeRespository.save(trade);


        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionKey(tradeParam.getProcessKey());

        if (processDefinitionQuery==null){
            log.error("当前流程Key不存在，请检查");
        }
        Map<String,Object> processVar = new HashMap<>();
        processVar.put("trade", trade);
        processVar.put("oneApprove", "liuhao");

        // 第一个参数，发起流程的ID  第二个参数业务ID ，第三个参数是将业务对象设置到流程变量中，可不设置
        runtimeService.startProcessInstanceByKey(tradeParam.getProcessKey(), tradeParam.getOrderNo() ,processVar);


    }


    @Autowired
    @Transactional
    public List<TaskVO> findAllTask(){
        List<Task> list = taskService.createTaskQuery().list();

        List<TaskVO> userTaskList  =new ArrayList<>();

        list.forEach(item -> {
            TaskVO userTask =  new TaskVO();
            BeanUtils.copyProperties(item,userTask);
            userTaskList.add(userTask);
        });


        return userTaskList;
    }

    @Override
    public String completeProcess(Complete complete) {

        Trade byOrderNo = tradeRespository.findByOrderNo(complete.getOrderNo());
        byOrderNo.setStatus(2);
        //其他字段酌情处理

        tradeRespository.save(byOrderNo);
        Map<String,Object> processVar = new HashMap<>();
        processVar.put("twoApprove", complete.getAssigness());
        processVar.put("trade", byOrderNo);


        taskService.complete(complete.getTaskId(),processVar);

        return "处理完成";
    }
}
