package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.service.AssociationBuilder;
import com.wei.myblog.service.CommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    AssociationBuilder associationBuilder;

   private Map<String, Object> getResultMapWithTotalNum(List<Comment> comments){
       Map<String, Object> resultMap = new HashMap<>();
       resultMap.put("totalNum", commentService.getTotalCommentNum());
       resultMap.put("commentList", comments);
       return resultMap;
   }

    @GetMapping("/listComments")
    public Result listComments(@RequestParam("begin") Integer begin
            , @RequestParam("count") Integer count){
        List<Comment> comments = commentService.listComments(begin, count);
        Map<String, Object> resultMapWithTotalNum = getResultMapWithTotalNum(comments);
        return Result.succeed(resultMapWithTotalNum);
    }

    @GetMapping("/listCommentsByUserId")
    public Result listCommentsByUserId(String userId){
        List<Comment> comments = commentService.listCommentsByUserId(userId);
        return Result.succeed(comments);
    }

    @GetMapping("/listCommentsByArticleId")
    public Result listCommentsByArticleId(@RequestParam("articleId") String articleId){
        List<Comment> comments = commentService.listCommentsByArticleId(articleId);
        return Result.succeed(comments);
    }

    @PostMapping("/saveComment")
    @RequiresRoles(value = {"admin", "predestined"}, logical =  Logical.OR)
    public Result saveComment(Comment comment, String userId, String articleId){
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
        commentService.saveComment(comment);
        /**
         * 把评论分别与用户和文章关联起来
         */
        String commentId = commentService.getCommentIdByCommentContent(comment.getCommentContent());
        associationBuilder.associateUserWithComment(userId, commentId);
        associationBuilder.associateArticleWithComment(articleId, commentId);
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
//        if (comment.getCommentContent().equals("") || comment.getCommentContent() == null){
//            return Result.fail("评论不能为空");
//        }
        commentService.updateComment(comment);
        return Result.succeed();
    }

    @GetMapping("/removeCommentByCommentId")
    @RequiresRoles(value = {"admin"})
    public Result removeCommentByCommentId(String commentId){
        commentService.removeCommentByCommentId(commentId);
        return Result.succeed();
    }
}
