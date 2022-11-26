package com.meowsauce.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meowsauce.dto.CommentDTO;
import com.meowsauce.dto.TitleDTO;
import com.meowsauce.entity.Comment;
import com.meowsauce.entity.CommentsVisitor;
import com.meowsauce.entity.Visitor;
import com.meowsauce.redis.RedisService;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.CommentService;
import com.meowsauce.service.CommentsVisitorService;
import com.meowsauce.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisService redisService;

    @GetMapping("/api/comment/{id}")
    //根据文章id列出所有评论信息及关联的访客信息
    public Result findAllCommentMessageByAId(@PathVariable("id") int id){
        List<Comment> comments = commentService.findAllCommentsByArticleId(id);
        List<CommentDTO> cds = commentService.insertCommentDTOMsg(comments);
        //处理评论结构与MenuController类似
        handleComments(cds);
        return ResultFactory.buildSuccessResult(cds);
    }

    //整合查询出的菜单数据
    public void handleComments(List<CommentDTO> commentDTOS){
        for (CommentDTO commentDTO : commentDTOS){
            List<CommentDTO> children = getAllByParentId(commentDTO.getCId(),commentDTOS);
            commentDTO.setChildren(children);
        }
        Iterator<CommentDTO> iterator = commentDTOS.iterator();
        while (iterator.hasNext()){
            CommentDTO commentDTO = iterator.next();
            if (commentDTO.getParentCommentId() != 0){
                iterator.remove();
            }
        }
    }
    //根据评论父id查询子评论
    private List<CommentDTO> getAllByParentId(int cid,List<CommentDTO> commentDTOS){
        ArrayList<CommentDTO> list = new ArrayList<>();
        for (CommentDTO cds : commentDTOS){
            if (cid == cds.getParentCommentId()){
                list.add(cds);
            }
        }
        return list;
    }
    //保存评论
    @PostMapping("/api/comment/save")
    public Result saveComment(@RequestBody CommentDTO commentDTO){
        commentService.saveComment(commentDTO);
        redisService.delete("getCarouselMsg");
        return ResultFactory.buildSuccessResult("评论成功");
    }

    //后台获取文章的评论
    @GetMapping("/api/admin/comment/{articleId}")
    public Result getCommentByArticleId(@PathVariable("articleId") int articleId){
        List<CommentDTO> commentDTOS = commentService.getCommentByArticleId(articleId);
        return ResultFactory.buildSuccessResult(commentDTOS);
    }
    //后台根据Id删除评论
    @PostMapping("/api/admin/comment/delete")
    public Result deleteCommentByCId(@RequestBody CommentDTO commentDTO){
        commentService.deleteCommentByCId(commentDTO);
        redisService.delete("getCarouselMsg");
        return ResultFactory.buildSuccessResult("删除成功");
    }
    /**
     * 最新评论
     * @return
     */
    //根据时间查询最新的六条评论
    @CrossOrigin
    @GetMapping("/api/comment/newComment")
    public Result getAllCommentByTime(){
       List<CommentDTO> comments =  commentService.getAllCommentDTOById();
       return ResultFactory.buildSuccessResult(comments);
    }

    //点击文章后在主图位置显示文章标题以及相关信息
    @GetMapping("/api/pageTitle/{id}")
    public Result getPageTitleByArticleId(@PathVariable("id") int id){
        TitleDTO titleDTO = commentService.getPageTitleByArticleId(id);
        return ResultFactory.buildSuccessResult(titleDTO);
    }

    //轮播图信息
    @GetMapping("/api/getCarouselMsg")
    public Result getCarouselMsg(){
        String key = "getCarouselMsg";
        Object redisCache = redisService.get(key);
        List<TitleDTO> list;
        if (redisCache == null){
            list = commentService.getAllArticle();
            redisService.set("getCarouselMsg",list);
        }else {

        }
        list = commentService.getAllArticle();
        return ResultFactory.buildSuccessResult(list);
    }
}
