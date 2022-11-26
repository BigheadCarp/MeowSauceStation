package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.Visitor;

public interface VisitorService extends IService<Visitor> {
    //根据评论id查找访客
    Visitor findVisitorByCommentId(int cid);
}
