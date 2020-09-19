package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.service.CommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 获取带总数的结果集
     * @param comments
     * @return
     */
   private Map<String, Object> getResultMapWithTotalNum(List<Comment> comments){
       Map<String, Object> resultMap = new HashMap<>();
       resultMap.put("totalNum", commentService.getTotalCommentNum());
       resultMap.put("commentList", comments);
       return resultMap;
   }

    /**
     * 列出评论
     * @param begin
     * @param count
     * @return
     */
    @GetMapping("/listComments")
    public Result listComments(@RequestParam("begin") Integer begin
            , @RequestParam("count") Integer count){
        List<Comment> comments = commentService.listComments(begin, count);
        Map<String, Object> resultMapWithTotalNum = getResultMapWithTotalNum(comments);
        return Result.succeed(resultMapWithTotalNum);
    }

    /**
     * 根据文章id列出所有评论
     * @param articleId
     * @return
     */
    @GetMapping("/listCommentsByArticleId")
    public Result listCommentsByArticleId(@RequestParam("articleId") String articleId){
        List<Comment> comments = commentService.listCommentsByArticleId(articleId);
        return Result.succeed(comments);
    }

    /**
     * 保存评论
     * @param comment
     * @param userId
     * @param articleId
     * @return
     */
    @PostMapping("/saveComment")
    @RequiresRoles(value = {"admin", "predestined"}, logical =  Logical.OR)
    public Result saveComment(Comment comment
            ,@RequestParam("userId") String userId
            ,@RequestParam("articleId") String articleId){
        commentService.saveComment(comment, userId, articleId);
        return Result.succeed();
    }

    /**
     * 这里可以更改别人的评论，待修复
     * @param comment
     * @return
     */
    @PostMapping("/updateComment")
    @RequiresRoles(value = {"admin", "predestined"}, logical = Logical.OR)
    public Result updateComment(Comment comment){
        if (comment.getCommentId() == null){
            return Result.fail("commentId不能为空");
        }
        commentService.updateComment(comment);
        return Result.succeed();
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @GetMapping("/removeComment")
    @RequiresRoles(value = {"admin"})
    public Result removeComment(@RequestParam("commentId") String commentId){
        commentService.removeCommentByCommentId(commentId);
        return Result.succeed();
    }
}
