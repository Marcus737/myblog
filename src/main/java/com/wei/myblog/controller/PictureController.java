package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Picture;
import com.wei.myblog.service.PictureService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myblog/picture")
public class PictureController {

    @Autowired
    PictureService pictureService;

    @GetMapping("/listPictures")
    public Result listPictures(){
        List<Picture> pictures = pictureService.listPictures();
        return Result.succeed(pictures);
    }

    @RequiresRoles("admin")
    @GetMapping("/removePicture")
    @RequiresAuthentication
    public Result removePicture(String pictureId){
        pictureService.removePicture(pictureId);
        return Result.succeed();
    }
}
