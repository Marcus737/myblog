package com.wei.myblog.controller;


import com.wei.myblog.common.Result;
import com.wei.myblog.entity.Article;
import com.wei.myblog.entity.Label;
import com.wei.myblog.entity.Picture;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@RestController
@RequestMapping("/myblog/file")
public class FileController {

    private static final String BASE_DIR_AVATAR = "avatar";
    private static final String BASE_DIR_MARKDOWN = "markdown";
    private static final String BASE_DIR_COVER = "cover";
    private static final String BASE_DIR_PICTURE = "picture";
    private static final String MARKDOWN_EXTENSION = ".md";

    @Autowired
    FileService fileService;

    @Autowired
    ArticleService articleService;

    @Autowired
    PictureService pictureService;

    @Autowired
    UserService userService;

    @Autowired
    AssociationBuilder associationBuilder;

    @GetMapping("/download")
    public void download(String path, HttpServletResponse response) {
        fileService.download(path, response);
    }

    @PostMapping("/uploadPicture")
    @RequiresAuthentication
    @RequiresRoles(value = {"admin"})
    public Result uploadPicture(@RequestParam("picture") MultipartFile avatar
            , @RequestParam("fileExtension") String fileExtension){
        try {
            String picturePath = fileService.upload(avatar, BASE_DIR_PICTURE, fileExtension);
            return Result.succeed(picturePath);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatar
            , @RequestParam("fileExtension") String fileExtension){
        try {
            /**
             * 获取本地文件路径
             */
            String localPath = fileService.upload(avatar, BASE_DIR_AVATAR, fileExtension);
            /**
             * 记录传入的图片
             */
            Picture picture = new Picture();
            picture.setPictureTime(new Timestamp(System.currentTimeMillis()));
            picture.setPictureUrl(localPath);
            pictureService.savePicture(picture);

            return Result.succeed(localPath);
        }catch (Exception e){
            return Result.fail("上传用户头像失败");
        }
    }

    @PostMapping("/updateAvatar")
    @RequiresAuthentication
    @RequiresRoles(value = {"admin", "predestined"})
    public Result updateAvatar(@RequestParam("avatar") MultipartFile avatar
            , @RequestParam("fileExtension") String fileExtension
            , @RequestParam("username") String username){
        try {
            /**
             * 获取本地文件路径
             */
            String localPath = fileService.upload(avatar, BASE_DIR_AVATAR, fileExtension);
            /**
             * 记录传入的图片
             */
            Picture picture = new Picture();
            picture.setPictureTime(new Timestamp(System.currentTimeMillis()));
            picture.setPictureUrl(localPath);
            pictureService.savePicture(picture);
            /**
             * 给用户设置头像
             */
            User user = new User();
            user.setUsername(username);
            user.setAvatar(localPath);
            userService.updateUser(user);

            return Result.succeed();
        }catch (Exception e){
            return Result.fail("上传用户头像失败");
        }
    }

    @PostMapping("/uploadArticle")
    @RequiresAuthentication
    @RequiresRoles(value = {"admin"})
    public Result uploadArticle(@RequestParam("article") MultipartFile articleFile
            , @RequestParam("coverExtension") String coverExtension
            , @RequestParam("cover") MultipartFile cover
            , @RequestParam("title") String title
            , @RequestParam("author") String author
            , @RequestParam("briefDescription") String briefDescription
            , @RequestParam(value = "clicks", required = false, defaultValue = "0")Integer clicks
            , @RequestParam("userId") String userId){
        try {
            /**
             * 获取本地文件路径
             */
            String markdownPath = fileService.upload(articleFile, BASE_DIR_MARKDOWN, MARKDOWN_EXTENSION);
            String coverPath = fileService.upload(cover, BASE_DIR_COVER, coverExtension);
            /**
             * 记录传入的图片
             */
            Picture picture = new Picture();
            picture.setPictureTime(new Timestamp(System.currentTimeMillis()));
            picture.setPictureUrl(coverPath);
            pictureService.savePicture(picture);
            /**
             * 保存markdown
             */
            Article article = new Article();
            article.setTitle(title);
            article.setAuthor(author);
            article.setBriefDescription(briefDescription);
            article.setClicks(clicks);
            article.setMarkdownUrl(markdownPath);
            article.setCover(coverPath);
            article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            articleService.saveArticle(article);
            /**
             * 建立user关联
             */
            String aid = articleService.getArticleIdByTitle(title);
            associationBuilder.associateUserWithArticle(userId, aid);
            return Result.succeed();
        }catch (Exception e){
            return Result.fail("上传文章失败");
        }
    }
}
