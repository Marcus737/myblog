package com.wei.myblog.config;

import com.wei.myblog.filter.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class MyShiroConfig {

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap=new LinkedHashMap<>();
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //不要用HashMap来创建Map，会有某些配置失效，要用链表的LinkedHashmap
        Map<String,String> filterRuleMap=new LinkedHashMap<>();
        //放行接口
        filterRuleMap.put("/myblog/article/listArticleByLabelId", "anon");
        filterRuleMap.put("/myblog/article/serArticle", "anon");
        filterRuleMap.put("/myblog/article/listArticles", "anon");
        filterRuleMap.put("/myblog/article/listArticleByUserId", "anon");
        filterRuleMap.put("/myblog/article/getArticleByTitle", "anon");
        filterRuleMap.put("/myblog/label/listLabels", "anon");
        filterRuleMap.put("/myblog/music/**", "anon");
        filterRuleMap.put("/myblog/file/**", "anon");
        filterRuleMap.put("/myblog/file/uploadAvatar", "anon");
        filterRuleMap.put("/myblog/user/saveUser", "anon");
        filterRuleMap.put("/myblog/security/**", "anon");
        filterRuleMap.put("/myblog/comment/listCommentsByArticleId", "anon");
        filterRuleMap.put("/myblog/comment/saveComment", "anon");
        filterRuleMap.put("/druid/**", "anon");
        //拦截所有接口
        filterRuleMap.put("/**","jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(CustomRealm customRealm){
        //设置自定义Realm
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO=new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 授权属性源配置
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
