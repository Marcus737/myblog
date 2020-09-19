package com.wei.myblog.dao;

import com.wei.myblog.entity.Article;
import com.wei.myblog.service.LabelService;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDao {

    @Select("select count(*) from article")
    Integer getTotalArticleNum();

    @Select("select * from article where article_id in (select article_id from article_label where label_id = #{labelId})")
    List<Article> listArticleByLabelId(String labelId);

    /**
     * 查询指定数量的文章
     * 查询前n条记录： limit 0, n
     * @param begin 开始的下标
     * @param count 文章的数量
     * @return 文章列表
     */
    @Select("select article_id, title, brief_description, author, update_time, clicks, markdown_url, cover from article limit #{begin}, #{count}")
    List<Article> listArticles(Integer begin, Integer count);

    /**
     * 查询用户所属的文章
     * @param userId 用户id
     * @return 文章列表
     */
    @Select("select article_id, title, brief_description, author, update_time, clicks, markdown_url, cover from article where article_id in (select article_id from user_article where user_id = #{userId})")
    List<Article> listArticleByUserId(String userId);

    @Select("select article_id, title, brief_description, author, update_time, clicks, markdown_url, cover from article where title like concat('%',#{keyword},'%')")
    List<Article> searchArticleByKeyword(String keyword);

    /**
     * 根据文章名查询文章
     * @param title 文章名
     * @return 文章信息
     */
    @Select("select article_id, title, brief_description, author, update_time, clicks, markdown_url, cover from article where title = #{title}")
    Article getArticleByTitle(String title);


    /**
     * 保存文章
     * @param article 文章信息
     */
    @Insert("insert into article(title, brief_description, author, update_time, clicks, markdown_url, cover) values (#{title}, #{briefDescription}, #{author}, #{updateTime}, #{clicks}, #{markdownUrl}, #{cover})")
    @Options(useGeneratedKeys = true, keyProperty = "articleId", keyColumn = "article_id")
    void saveArticle(Article article);

    /**
     * 更新文章
     * @param article 文章信息
     */
    @Update({"<script>"
            + "update article set"
            + "<trim suffixOverrides=','>"
            + "<if test='title != null'> title = #{title},</if>"
            + "<if test='briefDescription != null'> brief_description = #{briefDescription},</if>"
            + "<if test='author != null'> author = #{author}</if>,"
            + "<if test='updateTime != null'> update_time = #{updateTime},</if>"
            + "<if test='clicks != null'> clicks = #{clicks},</if>"
            + "<if test='markdownUrl != null'> markdown_url = #{markdownUrl},</if>"
            + "<if test='cover != null'> cover = #{cover}</if>"
            + "</trim>"
            + "where article_id = #{articleId}"
            + "</script>"})
    void updateArticle(Article article);

    /**
     * 根据文章id删除文章
     * @param articleId 文章id
     */
    @Delete("delete from article where article_id = #{articleId}")
    void removeArticleByArticleId(String articleId);
}
