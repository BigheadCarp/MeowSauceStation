package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.Signature;

import java.util.List;

public interface SignatureService extends IService<Signature> {
    List<Signature> getAllSignature();
}
