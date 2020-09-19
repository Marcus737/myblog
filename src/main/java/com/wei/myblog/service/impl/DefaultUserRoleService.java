package com.wei.myblog.service.impl;

import com.wei.myblog.dao.UserRoleDao;
import com.wei.myblog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultUserRoleService implements UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public void associated(String userId, String roleId) {
        userRoleDao.associated(userId, roleId);
    }

    @Override
    public void unAssociation(String userId, String roleId) {
        userRoleDao.unAssociation(userId, roleId);
    }

    @Override
    public void removeAll(String roleId) {
        userRoleDao.removeAll(roleId);
    }

    @Override
    public void removeAllByUserId(String userId) {
        userRoleDao.removeAllByUserId(userId);
    }
}
