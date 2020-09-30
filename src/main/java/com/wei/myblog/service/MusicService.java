package com.wei.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wei.myblog.dto.MusicInfo;
import com.wei.myblog.utils.HttpsUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MusicService {

    private Random random = new Random();

    private String getMusicSourceUrl = "http://search.kuwo.cn/r.s?"
            + "client=kt&all=%s&pn=0&rn=%s&uid=374255680&ver=kwplayer_ar_9.0.4.2"
            + "&vipver=1&ft=music&cluster=0&strategy=2012&encoding=utf8&rformat=json&vermerg=1&mobi=1";

    private String getMusicSrcUrl = "http://www.kuwo.cn/url?"
            + "format=mp3&response=url&type=convert_url&rid=%s&br=320kmp3";

    private String getMusicPicUrl = "http://artistpicserver.kuwo.cn/pic.web?"
            + "user=86740104241033&prod=kwplayer_ar_9.0.4.2&corp=kuwo&source=kwplayer_ar_9.0.4.2_hw.apk"
            + "&type=big_pic&pictype=url&content=list"
            + "&rid=%s&name=%s&filename=&width=600&height=600";
    
//    private static String getMusicLrcUrl = "http://m.kuwo.cn/newh5/singles/songinfoandlrc?musicId=%s";


    private String getMusicSource(String serKey, String resNum) {
        try {
//            String encodeSerKey = URLEncoder.encode(serKey, "UTF-8");
            String formatUrl = String.format(getMusicSourceUrl, serKey, resNum);
            return HttpsUtils.sendGet(formatUrl, null);
        }catch (Exception e){
            return null;
        }
    }

    private void getMusicSrc(List<MusicInfo> musicInfos){
        for (MusicInfo musicInfo : musicInfos){
            String rId = musicInfo.getMusicRId();
            String formatUrl = String.format(getMusicSrcUrl, rId);
            try {
                musicInfo.setSrc(HttpsUtils.sendGet(formatUrl, null));
            }catch (Exception e){}
        }
    }

    /**
     * 因为是json格式的歌词，以后再搞
     * @param musicInfos
     */
//    private void getMusicLrc(List<MusicInfo> musicInfos){
//
//    }

    private void getMusicPic(List<MusicInfo> musicInfos){
        for (MusicInfo musicInfo : musicInfos){
            try {
                String encodeMusicName = URLEncoder.encode(musicInfo.getTitle(), "UTF-8");
                String rId = musicInfo.getMusicId();
                String formatUrl = String.format(getMusicPicUrl, rId, encodeMusicName);
                String result = HttpsUtils.sendGet(formatUrl, null);
                JSONObject parseObject = JSONObject.parseObject(result);
                JSONArray array = parseObject.getJSONArray("array");
                int size = array.size();
                int index = random.nextInt(size);
                JSONObject object = JSONObject.parseObject(array.get(index).toString());
                String url = object.getString("url");
                musicInfo.setPic(url);
            }catch (Exception e){}
        }
    }


    private MusicInfo getBaseInfo(MusicInfo musicInfo, Object source){
        JSONObject temp = JSONObject.parseObject(source.toString());
        String artist = temp.getString("ARTIST");
        String songName = temp.getString("SONGNAME");
        String musicRId = temp.getString("MUSICRID");
        String musicId = musicRId.substring(musicRId.indexOf("_") + 1);
        musicInfo.setArtist(artist);
        musicInfo.setTitle(songName);
        musicInfo.setMusicRId(musicRId);
        musicInfo.setMusicId(musicId);
        return musicInfo;
    }

    public List<MusicInfo> getMusicInfo(String serKey, String resultNum) {
        List<MusicInfo> musicList = new ArrayList();
        String metaSource = new MusicService().getMusicSource(serKey, resultNum);
        JSONObject jsonObject = JSONObject.parseObject(metaSource);
        JSONArray abslist = jsonObject.getJSONArray("abslist");
        for (Object subSource : abslist) {
            MusicInfo tempMusicInfo = getBaseInfo(new MusicInfo(), subSource);
            musicList.add(tempMusicInfo);
        }
        getMusicSrc(musicList);
        getMusicPic(musicList);
        return musicList;
    }
}
