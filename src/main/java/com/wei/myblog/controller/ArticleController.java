package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Article;
import com.wei.myblog.service.ArticleService;
import com.wei.myblog.service.AssociationBuilder;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    AssociationBuilder associationBuilder;

    private Map<String, Object> createResultMap(Integer totalArticleNum, List<Article> articles){
        Map<String, Object> totalArticleNumAndArticles = new HashMap<>();
        totalArticleNumAndArticles.put("totalArticleNum", totalArticleNum);
        totalArticleNumAndArticles.put("articles", articles);
        return totalArticleNumAndArticles;
    }

    private Map<String, Object>  getTotalNumAndArticles(List<Article> articles, String userId, String serArticle, String labelId){
        Integer totalArticleNum;
        if (userId == null && serArticle == null && labelId == null ){
            totalArticleNum = articleService.getTotalArticleNum();
        }else {
            totalArticleNum = articles.size();
        }
        return createResultMap(totalArticleNum, articles);
    }

    @GetMapping("/listArticleByLabelId")
    public Result listArticleByLabelId(@RequestParam(value = "labelId") String labelId){
        List<Article> articles = articleService.listArticleByLabelId(labelId);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, null, null, labelId);
        return Result.succeed(totalNumAndArticles);
    }


    @GetMapping("/serArticle")
    public Result searchArticle(@RequestParam(value = "keyword") String keyword){
        List<Article> articles = articleService.searchArticleByKeyword(keyword);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, null, keyword, null);
        return Result.succeed(totalNumAndArticles);
    }

    @GetMapping("/listArticles")
    public Result listArticles(@RequestParam(value = "begin")Integer begin, @RequestParam(value = "count")Integer count){
        List<Article> articles = articleService.listArticles(begin, count);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, null, null, null);
        return Result.succeed(totalNumAndArticles);
    }

    @GetMapping("/listArticleByUserId")
    public Result listArticleByUserId(@RequestParam(value = "userId") String userId){
        List<Article> articles = articleService.listArticleByUserId(userId);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, userId, null, null);
        return Result.succeed(totalNumAndArticles);
    }

    @GetMapping("/getArticleByTitle")
    public Result getArticleByTitle(String title){
        Article article = articleService.getArticleByTitle(title);
        return Result.succeed(article);
    }

    @PostMapping("/updateArticle")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result updateArticle(Article article){
        articleService.updateArticle(article);
        return Result.succeed();
    }

    @GetMapping("/removeArticleByArticleId")
    @RequiresAuthentication
    @RequiresRoles("admin")
    public Result removeArticleByArticleId(String articleId){
        associationBuilder.removeAllFromArticleId(articleId);
        articleService.removeArticleByArticleId(articleId);
        return Result.succeed();
    }
}
