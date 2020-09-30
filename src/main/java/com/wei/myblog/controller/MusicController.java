package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.dto.MusicInfo;
import com.wei.myblog.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/myblog/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    @PostMapping("/serSong")
        public Result serSong(@RequestParam(name = "songName") String songName
            , @RequestParam(required = false, name = "num", defaultValue = "20") String num) {
        songName = songName.trim(); // 去除空格
        if ("".equals(songName)){
            return Result.fail("输入内容不能为空");
        }
         List<MusicInfo> musicInfo = musicService.getMusicInfo(songName, num);
        return Result.succeed(musicInfo);
    }
}
