package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserCommentDao {

    @Insert("insert into user_comment values(#{userId}, #{commentId})")
    void associated(String userId, String commentId);

    @Delete("delete from user_comment where user_id = #{userId} and comment_id = #{commentId}")
    void unAssociated(String userId, String commentId);

    @Delete("delete from user_comment where user_id = #{userId}")
    void removeAll(String userId);

    @Select("select user_id from user_comment where comment_id = #{commentId}")
    String getUserIdByCommentId(String commentId);
}
