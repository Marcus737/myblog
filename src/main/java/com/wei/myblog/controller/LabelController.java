package com.wei.myblog.controller;


import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.AssociationBuilder;
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

    @Autowired
    AssociationBuilder associationBuilder;

    @GetMapping("/listLabels")
    public Result listLabels(){
        List<Label> labels = labelService.listLabels();
        return Result.succeed(labels);
    }

    @PostMapping("/saveLabel")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result saveLabel(Label label, String articleId){
        labelService.saveLabel(label);
        String labelId = labelService.getLabelIdByLabelName(label.getLabelName());
        associationBuilder.associateArticleWithLabel(articleId , labelId);
        return Result.succeed();
    }

    @GetMapping("/removeLabel")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result removeLabel(String labelId){
        labelService.removeLabel(labelId);
        return Result.succeed();
    }
}
