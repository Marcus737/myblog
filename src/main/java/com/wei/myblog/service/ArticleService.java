package com.wei.myblog.service;

import com.wei.myblog.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Integer getTotalArticleNum();

    List<Article> listArticleByLabelId(String labelId);

    List<Article> searchArticleByKeyword(String keyword);

    /**
     * 查询指定数量的文章
     * 查询前n条记录： limit 0, n
     * @param begin 开始的下标
     * @param count 文章的数量
     * @return 文章列表
     */
    List<Article> listArticles(Integer begin, Integer count);

    /**
     * 查询用户所属的文章
     * @param userId 用户id
     * @return 文章列表
     */
    List<Article> listArticleByUserId(String userId);

    /**
     * 根据文章名查询文章
     * @param title 文章名
     * @return 文章信息
     */
    Article getArticleByTitle(String title);


    /**
     * 保存文章
     * @param article 文章信息
     */
    void saveArticle(Article article, List<String> articleLabelIds, MultipartFile articleFile, MultipartFile articleCover) throws IOException;

    /**
     * 更新文章
     * @param article 文章信息
     */
    void updateArticle(Article article, List<String> articleLabelIds, MultipartFile articleFile, MultipartFile articleCover) throws IOException;

    /**
     * 根据文章id删除文章
     * @param articleId 文章id
     */
    void removeArticleByArticleId(String articleId);
}
