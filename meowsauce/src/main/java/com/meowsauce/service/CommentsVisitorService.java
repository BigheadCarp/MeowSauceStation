package com.meowsauce.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.CommentsVisitor;

import java.util.List;

public interface CommentsVisitorService extends IService<CommentsVisitor> {
    List<CommentsVisitor> selectList(QueryWrapper<CommentsVisitor> commentsVisitorQueryWrapper);

    void deleteList(QueryWrapper<CommentsVisitor> visitorQueryWrapper);
}
