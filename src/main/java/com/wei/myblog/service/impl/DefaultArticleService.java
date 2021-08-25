package com.wei.myblog.service.impl;

import com.wei.myblog.dao.ArticleDao;
import com.wei.myblog.dao.CommentDao;
import com.wei.myblog.dao.LabelDao;
import com.wei.myblog.entity.Article;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultArticleService implements ArticleService {

    private static final Integer KEYWORD_MAX_LENGTH = 100;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    LabelDao labelDao;

    private void getDetail(Article article){
        String articleId = article.getArticleId();
        List<Comment> comments = commentDao.listCommentsByArticleId(articleId);
        article.setComments(comments);
        List<Label> labels = labelDao.listLabelsByArticleId(articleId);
        article.setLabels(labels);
    }

    private void getListDetail(List<Article> articles){
        for (Article article: articles){
            getDetail(article);
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
    public String getArticleIdByTitle(String title) {
        return articleDao.getArticleIdByTitle(title);
    }

    @Override
    @Transactional
    public void saveArticle(Article article) {
        articleDao.saveArticle(article);
    }

    @Override
    @Transactional
    public void updateArticle(Article article) {
        articleDao.updateArticle(article);
    }

    @Override
    public void removeArticleByArticleId(String articleId) {
        articleDao.removeArticleByArticleId(articleId);
    }
}
