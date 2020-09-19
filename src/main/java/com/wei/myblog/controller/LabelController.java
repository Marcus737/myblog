package com.wei.myblog.controller;


import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.ArticleLabelService;
import com.wei.myblog.service.LabelService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myblog/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    /**
     * 列出所有标签
     * @return
     */
    @GetMapping("/listLabels")
    public Result listLabels(){
        List<Label> labels = labelService.listLabels();
        return Result.succeed(labels);
    }

    /**
     * 保存标签
     * @param label
     * @return
     */
    @PostMapping("/saveLabel")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result saveLabel(Label label){
        labelService.saveLabel(label);
        return Result.succeed();
    }

    /**
     * 删除标签
     * @param labelId
     * @return
     */
    @GetMapping("/removeLabel")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result removeLabel(String labelId){
        labelService.removeLabel(labelId);
        return Result.succeed();
    }
}
