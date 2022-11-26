package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.dto.CommentDTO;
import com.meowsauce.dto.TitleDTO;
import com.meowsauce.dto.TotalDTO;
import com.meowsauce.entity.Comment;

import java.util.ArrayList;
import java.util.List;

public interface CommentService extends IService<Comment> {
    //根据文章Id查找相关联的评论
    List<Comment> findAllCommentsByArticleId(int aid);


    List<CommentDTO> insertCommentDTOMsg(List<Comment> comments);

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> getCommentByArticleId(int articleTitle);

    void deleteCommentByCId(CommentDTO commentDTO);

    List<CommentDTO> getAllCommentDTOById();


    TotalDTO getTotal();

    TitleDTO getPageTitleByArticleId(int id);

    List<TitleDTO> getAllArticle();
}
