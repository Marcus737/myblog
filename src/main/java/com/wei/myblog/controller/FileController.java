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

    @Autowired
    FileService fileService;

    @GetMapping("/download")
    public void download(String path, HttpServletResponse response) {
        fileService.download(path, response);
    }
}
