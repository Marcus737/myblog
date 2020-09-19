package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Article;
import com.wei.myblog.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 创建结果集
     * @param totalArticleNum
     * @param articles
     * @return
     */
    private Map<String, Object> createResultMap(Integer totalArticleNum, List<Article> articles){
        Map<String, Object> totalArticleNumAndArticles = new HashMap<>();
        totalArticleNumAndArticles.put("totalArticleNum", totalArticleNum);
        totalArticleNumAndArticles.put("articles", articles);
        return totalArticleNumAndArticles;
    }

    /**
     *      * 返回封装总数目的结果集
     * @param articles
     * @param selectFromDataBase
     * @return
     */
    private Map<String, Object>  getTotalNumAndArticles(List<Article> articles, Boolean selectFromDataBase){
        Integer totalArticleNum;
        if (selectFromDataBase){
            totalArticleNum = articleService.getTotalArticleNum();
        }else {
            totalArticleNum = articles.size();
        }
        return createResultMap(totalArticleNum, articles);
    }

    /**
     * 根据labelId查询文章
     * @param labelId
     * @return
     */
    @GetMapping("/listArticleByLabelId")
    public Result listArticleByLabelId(@RequestParam(value = "labelId") String labelId){
        List<Article> articles = articleService.listArticleByLabelId(labelId);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, false);
        return Result.succeed(totalNumAndArticles);
    }

    /**
     * 根据文本标题查询文章
     * @param keyword
     * @return
     */
    @GetMapping("/serArticle")
    public Result searchArticle(@RequestParam(value = "keyword") String keyword){
        // 去空格
        keyword = keyword.trim();
        if (keyword.equals("")){
            return Result.fail("输入内容不能为空");
        }
        List<Article> articles = articleService.searchArticleByKeyword(keyword);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles,false);
        return Result.succeed(totalNumAndArticles);
    }

    /**
     * 列出文章
     * @param begin
     * @param count
     * @return
     */
    @GetMapping("/listArticles")
    public Result listArticles(@RequestParam(value = "begin")Integer begin, @RequestParam(value = "count")Integer count){
        List<Article> articles = articleService.listArticles(begin, count);
        Map<String, Object> totalNumAndArticles = getTotalNumAndArticles(articles, true);
        return Result.succeed(totalNumAndArticles);
    }

    /**
     * 更新文章（MDFile、title、briefDescription、）
     * @param article
     * @return
     */
    @PostMapping("/updateArticle")
    @RequiresRoles("admin")
    public Result updateArticle(Article article
            , @RequestParam(value = "articleLabelIds") List<String> articleLabelIds
            , @RequestParam(value = "articleFile", required = false) MultipartFile articleFile
            , @RequestParam(value = "articleCover", required = false) MultipartFile articleCover) throws IOException {
        articleService.updateArticle(article, articleLabelIds, articleFile, articleCover);
        return Result.succeed();
    }

    /**
     * 保存文章
     * @param article
     * @param articleLabelIds
     * @param articleFile
     * @param articleCover
     * @return
     * @throws IOException
     */
    @PostMapping("/saveArticle")
    @RequiresRoles("admin")
    public Result saveArticle(Article article
            , @RequestParam(value = "articleLabelIds") List<String> articleLabelIds
            , @RequestParam(value = "articleFile", required = false) MultipartFile articleFile
            , @RequestParam(value = "articleCover", required = false) MultipartFile articleCover) throws IOException {
        articleService.saveArticle(article, articleLabelIds, articleFile, articleCover);

        return Result.succeed();
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @GetMapping("/removeArticle")
    @RequiresRoles("admin")
    public Result removeArticle(@RequestParam("articleId") String articleId){
        articleService.removeArticleByArticleId(articleId);
        return Result.succeed();
    }
}
