package com.simai.gccx.processmanager.controller;


import com.simai.gccx.processmanager.model.Complete;
import com.simai.gccx.processmanager.model.TaskVO;
import com.simai.gccx.processmanager.service.GccxActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/act/process")
public class GccxActivitiController {


    @Autowired
    private GccxActivitiService gccxActivitiService;


    @PostMapping
    @RequestMapping("/deploy")
    public String deployProcee(@RequestParam("processFile") MultipartFile multipartFile) throws IOException {

        gccxActivitiService.deployProcess(multipartFile);
        return "部署成功" ;
    }


    @PostMapping
    @RequestMapping("/start")
    public String startProcess(@RequestBody TradeParam tradeParam){
        gccxActivitiService.startProcess(tradeParam);
        return "流程启动成功";
    }



    @PostMapping
    @RequestMapping("/list/task")
    public List<TaskVO> startProcess(){


        return gccxActivitiService.findAllTask();
    }


    @PostMapping
    @RequestMapping("/complete")
    public String completeProcess(@RequestBody Complete complete){


        return gccxActivitiService.completeProcess(complete);
    }



}
