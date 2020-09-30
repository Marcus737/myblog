package com.wei.myblog.controller;


import com.wei.myblog.common.Result;
import com.wei.myblog.config.FileConfig;
import com.wei.myblog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/myblog/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    FileConfig fileConfig;

    @GetMapping("/download")
    public void download(String path, HttpServletResponse response) {
        fileService.download(path, response);
    }

    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam(value = "file") MultipartFile file) throws IOException {
        String avatarPath = fileService.upload(file, fileConfig.getBaseDirAvatar(), fileConfig.getDefaultImgType());
        return Result.succeed(avatarPath);
    }
}
