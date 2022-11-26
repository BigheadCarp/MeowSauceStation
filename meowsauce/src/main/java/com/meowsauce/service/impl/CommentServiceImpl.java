package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.dto.CommentDTO;
import com.meowsauce.dto.TitleDTO;
import com.meowsauce.dto.TotalDTO;
import com.meowsauce.entity.Article;
import com.meowsauce.entity.Comment;
import com.meowsauce.entity.CommentsVisitor;
import com.meowsauce.entity.Visitor;
import com.meowsauce.mapper.ArticleMapper;
import com.meowsauce.mapper.CommentMapper;
import com.meowsauce.mapper.CommentsVisitorMapper;
import com.meowsauce.redis.RedisService;
import com.meowsauce.service.*;
import com.meowsauce.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LabelsService labelsService;
    @Autowired
    private CommentsVisitorService commentsVisitorService;
    //根据文章ID查询所有评论
    @Override
    public List<Comment> findAllCommentsByArticleId(int aid) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", aid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return comments;
    }
    //查询评论与对应的访客信息并封装至一个实体类中
    @Override
    public List<CommentDTO> insertCommentDTOMsg(List<Comment> comments) {
        ArrayList<CommentDTO> commentDTOS = new ArrayList<>();
        comments.forEach(c -> {
            Visitor visitor = visitorService.findVisitorByCommentId(c.getId());
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setCId(c.getId());
            commentDTO.setArticleId(c.getArticleId());
            commentDTO.setWebsite(visitor.getWebsite());
            Date date = c.getCommentDate();
            DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.LONG);
            DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.MEDIUM);
            commentDTO.setCommentDate(dateInstance.format(date) + " " + timeInstance.format(date));
            commentDTO.setCommentContent(c.getCommentContent());
            commentDTO.setParentCommentId(c.getParentCommentId());
            commentDTO.setNickName(visitor.getNickName());
            commentDTO.setCover(visitor.getCover());
            commentDTO.setEmail(visitor.getEmail());
            commentDTOS.add(commentDTO);
        });
        return commentDTOS;
    }
    //保存评论信息以及访客信息
    @Transactional
    @Override
    public void saveComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        Visitor visitor = new Visitor();
        CommentsVisitor commentsVisitor = new CommentsVisitor();
        comment.setArticleId(commentDTO.getArticleId());
        comment.setCommentContent(commentDTO.getCommentContent());
        comment.setCommentDate(new Date());
        comment.setParentCommentId(commentDTO.getParentCommentId());
        visitor.setNickName(commentDTO.getNickName());
        visitor.setCover(commentDTO.getCover());
        visitor.setEmail(commentDTO.getEmail());
        visitor.setWebsite(commentDTO.getWebsite());
        save(comment);
        visitorService.save(visitor);
        commentsVisitor.setCid(comment.getId());
        commentsVisitor.setVid(visitor.getId());
        commentsVisitorService.save(commentsVisitor);
    }
    //根据文章Id查询评论信息以及访客信息并封装成一个类
    @Override
    public List<CommentDTO> getCommentByArticleId(int articleId) {
        Article article = articleService.findById(articleId);
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("article_id", article.getId());
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        List<CommentDTO> commentDTOS = insertCommentDTOMsg(comments);
        return commentDTOS;
    }
    //后台评论管理删除评论信息
    @Override
    @Transactional
    public void deleteCommentByCId(CommentDTO commentDTO) {
        Comment comment = commentMapper.selectById(commentDTO.getCId());
        //删除关联表数据
        QueryWrapper<CommentsVisitor> visitorQueryWrapper = new QueryWrapper<>();
        visitorQueryWrapper.eq("cid", comment.getId());
        commentsVisitorService.deleteList(visitorQueryWrapper);
        //查找子评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_comment_id", comment.getId());
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        if (comments.size() > 0) {
            //删除子评论及子评论关联表数据
            comments.forEach(e -> {
                QueryWrapper<CommentsVisitor> wrapper = new QueryWrapper<>();
                wrapper.eq("cid", e.getId());
                commentsVisitorService.deleteList(wrapper);
            });
            commentMapper.deleteBatchIds(comments);
        }
        //删除评论
        commentMapper.deleteById(comment);
    }

    //最新评论组件根据id查询最新的六条评论
    @Override
    public List<CommentDTO> getAllCommentDTOById() {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentDTO> commentDTOS = insertCommentDTOMsg(comments);
        Collections.reverse(commentDTOS);
        if (commentDTOS.size() > 7) {
            commentDTOS = commentDTOS.subList(0, 6);
        } else {
            commentDTOS = commentDTOS.subList(0, commentDTOS.size());
        }
        return commentDTOS;
    }
    //归档页面以及卡片组件获取文章,标签,评论数量
    @Override
    public TotalDTO getTotal() {
        int articleSize = articleService.getAllArticle().size();
        int commentSize = commentMapper.selectList(null).size();
        int labelSize = labelsService.getAllLabel().size();
        TotalDTO totalDTO = new TotalDTO();
        totalDTO.setArticleTotal(articleSize);
        totalDTO.setCommentTotal(commentSize);
        totalDTO.setLabelTotal(labelSize);
        return totalDTO;
    }
    //主图根据文章Id获取文章信息以及评论信息并封装
    @Override
    public TitleDTO getPageTitleByArticleId(int id) {
        TitleDTO titleDTO = new TitleDTO();
        //查询评论数量
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id",id);
        Integer integer = commentMapper.selectCount(queryWrapper);
        titleDTO.setCommentNum(integer);
        //查询文章,导入文章标题及文章分类
        Article article = articleService.findById(id);
        titleDTO.setArticleTitle(article.getArticleTitle());
        titleDTO.setType(article.getArticleType());
        titleDTO.setId(article.getId());
        //传入博客封面
        titleDTO.setArticleCover(article.getArticleCover());
        //格式化时间
        DateFormat dateInstance = DateFormat.getDateInstance(DateFormat.LONG);
        String format = dateInstance.format(article.getArticleDate());
        titleDTO.setArticleDate(format);
        //格式化阅读数量
        Integer articleView = article.getArticleView();
        String viewResult;
        if (articleView > 1000){
            articleView = articleView / 1000;
             viewResult = articleView + "k";
        }else {
             viewResult = articleView + "";
        }
        titleDTO.setReadingNum(viewResult);

        return titleDTO;
    }
    //轮播图文章信息,与评论信息封装
    @Override
    public List<TitleDTO> getAllArticle() {
        ArrayList<TitleDTO> titleDTOS = new ArrayList<>();
        List<Article> articles = articleService.getArticleToHavingLooking();
        articles.forEach(e->{
            TitleDTO titleDTO = getPageTitleByArticleId(e.getId());
            titleDTOS.add(titleDTO);
        });
        return titleDTOS;
    }
}
