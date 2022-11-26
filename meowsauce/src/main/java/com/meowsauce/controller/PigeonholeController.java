package com.meowsauce.controller;

import com.meowsauce.dto.ArticleDTO;
import com.meowsauce.dto.TotalDTO;
import com.meowsauce.entity.Pigeonhole;
import com.meowsauce.mapper.PigeonholeMapper;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.ArticleService;
import com.meowsauce.service.CommentService;
import com.meowsauce.service.PigeonholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PigeonholeController {
    @Autowired
    private PigeonholeService pigeonholeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    //根据时间查询文章信息
    @GetMapping("/api/pigeonhole/getArticleByTime")
    public Result getArticleByTime(){
        List<ArticleDTO> articleDTOList = articleService.getArticleByTime();
        return ResultFactory.buildSuccessResult(articleDTOList);
    }
    //查询文章,评论以及标签的数量
    @GetMapping("/api/pigeonhole/getTotal")
    public Result getTotal(){
        TotalDTO totalDTOS = commentService.getTotal();
        return ResultFactory.buildSuccessResult(totalDTOS);
    }
    //查询更新信息
    @GetMapping("/api/pigeonhole/getAllUpdateMsg")
    public Result getAllUpdateMsg(){
        List<Pigeonhole> pigeonholes = pigeonholeService.selectUpdateMsgByTime();
        return ResultFactory.buildSuccessResult(pigeonholes);
    }
}
