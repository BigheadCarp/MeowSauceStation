package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.Pigeonhole;
import com.meowsauce.mapper.PigeonholeMapper;
import com.meowsauce.service.PigeonholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PigeonholeServiceImpl extends ServiceImpl<PigeonholeMapper,Pigeonhole> implements PigeonholeService {
    @Autowired
    private PigeonholeMapper pigeonholeMapper;
    //查询更新信息
    @Override
    public List<Pigeonhole> selectUpdateMsgByTime() {
        QueryWrapper<Pigeonhole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("pigeonhole_date");
        List<Pigeonhole> pigeonholes = pigeonholeMapper.selectList(queryWrapper);
        return pigeonholes;
    }
}
