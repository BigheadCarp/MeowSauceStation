package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.Pigeonhole;

import java.util.List;

public interface PigeonholeService extends IService<Pigeonhole> {
    List<Pigeonhole> selectUpdateMsgByTime();
}
