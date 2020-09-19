package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Picture;
import com.wei.myblog.service.PictureService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/myblog/picture")
@RequiresRoles("admin")
@RequiresAuthentication
public class PictureController {

    @Autowired
    PictureService pictureService;

    /**
     * 列出所有图片
     * @return
     */
    @GetMapping("/listPictures")
    public Result listPictures(){
        List<Picture> pictures = pictureService.listPictures();
        return Result.succeed(pictures);
    }

    /**
     * 保存图片
     * @param articleId
     * @param pictureImg
     * @return
     * @throws IOException
     */
    @PostMapping("/savePicture")
    public Result savePicture(String articleId, MultipartFile pictureImg) throws IOException {
        String pictureUrl = pictureService.savePicture(articleId, pictureImg);
        return Result.succeed(pictureUrl);
    }

    /**
     * 删除图片
     * @param articleId
     * @return
     */
    @GetMapping("/removePicture")
    public Result removePicture(String articleId){
        pictureService.removePicture(articleId);
        return Result.succeed();
    }
}
