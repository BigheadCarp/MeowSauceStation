package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.CommentsVisitor;
import com.meowsauce.mapper.CommentsVisitorMapper;
import com.meowsauce.service.CommentsVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsVisitorServiceImpl extends ServiceImpl<CommentsVisitorMapper, CommentsVisitor> implements CommentsVisitorService {
    @Autowired
    private CommentsVisitorMapper commentsVisitorMapper;
    //查询评论与访客关联信息
    @Override
    public List<CommentsVisitor> selectList(QueryWrapper<CommentsVisitor> commentsVisitorQueryWrapper) {
        List<CommentsVisitor> commentsVisitors = commentsVisitorMapper.selectList(commentsVisitorQueryWrapper);
        return commentsVisitors;
    }
    //删除评论与访客关联信息
    @Override
    public void deleteList(QueryWrapper<CommentsVisitor> visitorQueryWrapper) {
        commentsVisitorMapper.delete(visitorQueryWrapper);
    }
}
