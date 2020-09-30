package com.wei.myblog.service.impl;

import com.wei.myblog.config.FileConfig;
import com.wei.myblog.dao.ArticleDao;
import com.wei.myblog.entity.Article;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

import java.util.List;

@Service
public class DefaultArticleService implements ArticleService {

    public static final Integer KEYWORD_MAX_LENGTH = 100;

    @Autowired
    FileConfig fileConfig;

    @Autowired
    FileService fileService;

    @Autowired
    UserArticleService userArticleService;

    @Autowired
    ArticleCommentService articleCommentService;

    @Autowired
    ArticleLabelService articleLabelService;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    CommentService commentService;

    @Autowired
    LabelService labelService;

    private void getDetail(Article article){
        String articleId = article.getArticleId();
        List<Comment> comments = commentService.listCommentsByArticleId(articleId);
        article.setComments(comments);
        List<Label> labels = labelService.listLabelsByArticleId(articleId);
        article.setLabels(labels);
    }

    private void getListDetail(List<Article> articles){
        for (Article article: articles){
            getDetail(article);
        }
    }

    private void saveMD(Article article, MultipartFile articleFile) throws IOException {
        if (articleFile == null){
            return;
        }
        /**
         *   获得上传文件的路径
         */
        String uploadPath = fileService.upload(articleFile, fileConfig.getBaseDirMarkdown(), fileConfig.getMarkdownExtension());
        /**
         *  删除原来的文件
         */
        fileService.removeFile(article.getMarkdownUrl());
        /**
         *  路径与文章绑定
         */
        article.setMarkdownUrl(uploadPath);

    }

    private void saveCover(Article article, MultipartFile articleCover) throws IOException {
        if (articleCover == null){
            return;
        }
        String uploadPath = fileService.upload(articleCover, fileConfig.getBaseDirCover(), fileConfig.getDefaultImgType());
        fileService.removeFile(article.getCover());
        article.setCover(uploadPath);
    }

    private void saveLabels(String articleId, List<String> articleLabelIds) {
        /**
         *  解除与此文章关联的所有标签
         */
        articleLabelService.removeAllLabel(articleId);
        for (String labelId: articleLabelIds) {
            articleLabelService.associated(articleId, labelId);
        }
    }

    @Override
    public Integer getTotalArticleNum() {
        return articleDao.getTotalArticleNum();
    }

    @Override
    public List<Article> listArticleByLabelId(String labelId) {
        List<Article> articles = articleDao.listArticleByLabelId(labelId);
        getListDetail(articles);
        return articles;
    }

    @Override
    public List<Article> searchArticleByKeyword(String keyword) {
        if (keyword.length() > KEYWORD_MAX_LENGTH){
            throw new RuntimeException("搜索关键词过长");
        }
        List<Article> articles = articleDao.searchArticleByKeyword(keyword);
        getListDetail(articles);
        return articles;
    }

    @Override
    public List<Article> listArticles(Integer begin, Integer count) {
        List<Article> articles = articleDao.listArticles(begin, count);
        getListDetail(articles);
        return articles;
    }

    @Override
    public List<Article> listArticleByUserId(String userId) {
        List<Article> articles = articleDao.listArticleByUserId(userId);
        getListDetail(articles);
        return articles;
    }

    @Override
    public Article getArticleByTitle(String title) {
        Article article = articleDao.getArticleByTitle(title);
        getDetail(article);
        return article;
    }


    @Override
    @Transactional
    public void saveArticle(Article article, List<String> articleLabelIds, MultipartFile articleFile, MultipartFile articleCover) throws IOException {
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        saveMD(article, articleFile);
        saveCover(article, articleCover);
        /**
         *  点击数不能为0 ___ 0.0
         */
        article.setClicks(0);
        articleDao.saveArticle(article);
        saveLabels(article.getArticleId() ,articleLabelIds);
    }

    @Override
    @Transactional
    public void updateArticle(Article article, List<String> articleLabelIds, MultipartFile articleFile, MultipartFile articleCover) throws IOException {
        saveMD(article, articleFile);
        saveCover(article, articleCover);
        saveLabels(article.getArticleId() ,articleLabelIds);
        articleDao.updateArticle(article);
    }

    /**
     *  删除文章
     * @param articleId
     */
    @Override
    @Transactional
    public void removeArticleByArticleId(String articleId) {

        String userIdByArticleId = userArticleService.getUserIdByArticleId(articleId);

        /**
         *  删除用户与文章的关联
         */
        userArticleService.unAssociatedUserWithArticle(userIdByArticleId, articleId);

        /**
         *  删除文章评论
         */
        articleCommentService.removeAll(articleId);

        /**
         * 删除文章标签
         */
        articleLabelService.removeAllLabel(articleId);

        /**
         *  删除文章
         */
        articleDao.removeArticleByArticleId(articleId);
    }
}
