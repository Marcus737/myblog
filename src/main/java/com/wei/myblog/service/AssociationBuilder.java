package com.wei.myblog.service;

import com.wei.myblog.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssociationBuilder {

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    UserArticleDao userArticleDao;

    @Autowired
    UserCommentDao userCommentDao;

    @Autowired
    ArticleCommentDao articleCommentDao;

    @Autowired
    ArticleLabelDao articleLabelDao;

    @Autowired
    RolePermissionDao rolePermissionDao;



    /**
     * 建立user与role的关联
     * @param userId 用户id
     * @param roleId 角色id
     */
    public void associateUserWithRole(String userId, String roleId){
        userRoleDao.associated(userId, roleId);
    }

    /**
     * 建立role与permission的关联
     * @param roleId 角色id
     * @param permissionId 权限id
     */
    public void associateRoleWithPermission(String roleId, String permissionId){
        rolePermissionDao.associated(roleId, permissionId);
    }

    public void associateUserWithComment(String userId, String commentId){
        userCommentDao.associated(userId, commentId);
    }

    public void associateUserWithArticle(String userId, String articleId){
        userArticleDao.associated(userId, articleId);
    }

    public void associateArticleWithComment(String articleId, String commentId){
        articleCommentDao.associated(articleId, commentId);
    }

    public void associateArticleWithLabel(String articleId, String labelId){
        articleLabelDao.associated(articleId, labelId);
    }

    public void unAssociateArticleWithLabel(String articleId, String labelId){
        articleLabelDao.unAssociated(articleId, labelId);
    }

    public void unAssociateArticleWithComment(String articleId, String commentId){
        articleCommentDao.unAssociated(articleId, commentId);
    }

    public void unAssociateUserWithArticle(String userId, String articleId){
        userArticleDao.unAssociated(userId, articleId);
    }

    public void unAssociateUserWithComment(String userId, String commentId){
        userCommentDao.unAssociated(userId, commentId);
    }

    /**
     * 解除关联
     * @param userId 用户id
     */
    public void unAssociateUserWithRole(String userId, String roleId){
        userRoleDao.unAssociation(userId, roleId);
    }

    /**
     * 解除关联
     * @param roleId 角色id
     */
    public void unAssociateRoleWithPermission(String roleId, String permissionId){
        rolePermissionDao.unAssociation(roleId, permissionId);
    }

   public void removeAllFromUserId(String userId){
        userRoleDao.removeAll(userId);
        userArticleDao.removeAll(userId);
        userCommentDao.removeAll(userId);
   }

   public void removeAllFromRoleId(String roleId){
        rolePermissionDao.removeAll(roleId);
   }

   public void removeAllFromArticleId(String articleId){
        articleLabelDao.removeAll(articleId);
        articleCommentDao.removeAll(articleId);
   }
}
