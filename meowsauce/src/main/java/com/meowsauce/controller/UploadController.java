package com.meowsauce.controller;

import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.util.AliyunOSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
public class UploadController {
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;
    //TODO 格式校验安全级别低
    //上传图片
    @PostMapping("/api/uploadFile")
    public Result uploadBlog(@RequestParam("file")MultipartFile file){
        String fileName = file.getOriginalFilename();
        //简单格式校验
        String fileType = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0)fileType = fileName.substring(i+1);
        System.out.println("格式校验" + fileName);
        if (!"jpg".equals(fileType) || !"png".equals(fileType) || !"jpeg".equals(fileType)){
            return ResultFactory.buildFailResult("格式错误");
        }
        //格式校验结束
        System.out.println(fileName + "==filename");
        try{
            if (file != null){
                if (!"".equals(fileName.trim())){
                    File newFile = new File(fileName);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传至OSS
                    String uploadUrl = aliyunOSSUtil.upLoad(newFile);
                    return ResultFactory.buildSuccessResult(uploadUrl);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return ResultFactory.buildFailResult("上传失败");
    }
}
