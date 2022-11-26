package com.meowsauce.controller;

import com.meowsauce.entity.Signature;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class SignatureController {
    @Autowired
    private SignatureService signatureService;
    //查询所有签名
    @GetMapping("/api/signature/getAll")
    public Result getAllSignature(){
       List<Signature> signatures =  signatureService.getAllSignature();
       return ResultFactory.buildSuccessResult(signatures);
    }
    //保存签名
    @PostMapping("/api/content/signature/save")
    public Result SaveSignature(@RequestBody Signature signature){
        signature.setSignatureDate(new Date());
        signatureService.save(signature);
        return ResultFactory.buildSuccessResult("保存成功");
    }

    //删除标签
    @DeleteMapping("/api/content/signature/delete/{id}")
    public Result deleteSignature(@PathVariable("id") int id){
        signatureService.removeById(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }
}
