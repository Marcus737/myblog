package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.dto.MusicInfo;
import com.wei.myblog.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myblog/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    @GetMapping("/serSong")
        public Result serSong(@RequestParam(name = "songName") String songName
            , @RequestParam(required = false, name = "num", defaultValue = "20") String num) {
        List<MusicInfo> musicInfo = musicService.getMusicInfo(songName, num);
        return Result.succeed(musicInfo);
    }
}
