package com.meowsauce.controller;

import com.meowsauce.dto.LabelDTO;
import com.meowsauce.entity.Labels;
import com.meowsauce.redis.RedisService;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LabelController {
    @Autowired
    private LabelsService labelsService;
    //根据文章Id查询所有标签并以数组的形式保存
    @GetMapping("/api/getLabel/{id}")
    public Result getLabelByAidToArray(@PathVariable("id") int aid){
         String[]  labels =  labelsService.getAllLabelByAidToArray(aid);
       return ResultFactory.buildSuccessResult(labels);
    }
    //后台文章编辑时设立文章标签
    @PostMapping("/api/admin/content/label")
    public Result saveLabel(@RequestBody LabelDTO label){
        labelsService.saveLabelsByAId(label.getId(),label.getLabel());
        return ResultFactory.buildSuccessResult("保存成功");
    }
}
