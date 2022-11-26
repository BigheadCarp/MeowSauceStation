package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.CommentsVisitor;
import com.meowsauce.entity.Visitor;
import com.meowsauce.mapper.CommentsVisitorMapper;
import com.meowsauce.mapper.VisitorMapper;
import com.meowsauce.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {
    @Autowired
    private CommentsVisitorMapper commentsVisitorMapper;
    @Autowired
    private VisitorMapper visitorMapper;
    //根据评论Id查询访客信息
    @Override
    public Visitor findVisitorByCommentId(int cid) {
        QueryWrapper<CommentsVisitor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        CommentsVisitor commentsVisitor = commentsVisitorMapper.selectOne(queryWrapper);
        Visitor visitor = visitorMapper.selectById(commentsVisitor.getVid());
        return visitor;
    }

}
