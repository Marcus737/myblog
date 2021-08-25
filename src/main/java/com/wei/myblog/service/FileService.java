package com.wei.myblog.service;

import com.wei.myblog.config.FileConfig;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Date;

/**
 * 文件操作工具类
 */
@Service
public class FileService {

    @Autowired
    private FileConfig fileConfig;

    public FileService() {
    }

    public void download(String path, HttpServletResponse response) {
        File file;
        InputStream is = null;
        OutputStream os = null;
        try {
            // path是指欲下载的文件的路径。
            file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Content-Length", "" + file.length());
            response.addHeader("Access-Control-Expose-Headers", "Access-Control-Expose-Headers");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Vary", "Access-Control-Request-Method");
            response.addHeader("Vary", "Origin");
            response.addHeader("Vary", "Access-Control-Request-Headers");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/octet-stream");
            // 以流的形式下载文件。
            is = new BufferedInputStream(new FileInputStream(file));
            os = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024 * 2];
            int len;
            while ((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("下载失败");
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException("文件下载输入流关闭失败");
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException("文件下载输出流关闭失败");
                }
            }
        }
    }

    /**
     *
     * @param baseDir 基础路径
     * @param file 文件
     * @param fileExtension 文件扩展名
     * @return 返回上传成功后的文件的绝对路径
     * @throws IOException io异常
     */
    public String upload(MultipartFile file, String baseDir, String fileExtension) throws IOException {
        //判断文件是否符合条件
        checkAllowed(file, fileExtension);
        //获取文件的绝对路径
        String fileName = extractFilename(file, baseDir,  fileExtension);
        //获取创建好的文件对象
        File desc = getAbsoluteFile(fileName);
        //写入内容
        file.transferTo(desc);
        return fileName;
    }

    /**
     * 拼接文件路径
     * @param file 文件对象
     * @param baseDir
     * @param fileExtension 文件扩展名
     * @return 拼接基础路径和日期后的字符串
     *
     *  The return value may be is D:/profile/upload/2020/08/14/6bbe9af6176b4f4c394f70cd608973d5.md
     */
    private String extractFilename(MultipartFile file, String baseDir, String fileExtension) {
        return fileConfig.getBasePath()
                + baseDir
                + "/"
                + DateFormatUtils.format(new Date(), "yyyy/MM/dd")
                + "/"
                + encodingFilename(file.getOriginalFilename())
                + fileExtension;
    }

    /**
     * 创建文件本身及父目录并返回该文件
     * @param filename 文件名
     * @return 创建好的文件对象
     *
     * File.separator 代表下划线，兼容linux和window
     */
    private File getAbsoluteFile(String filename){
        File desc = new File(File.separator + filename);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            try {
                desc.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("创建文件失败!");
            }
        }
        return desc;
    }

    /**
     * md5编码文件，防止重复文件名
     * @param filename 文件名
     * @return 编码后的字符串
     *
     * System.nanoTime()获取纳秒时间
     * The value returned represents nanoseconds since some fixed but
     * arbitrary <i>origin</i> time (perhaps in the future, so values
     * may be negative).
     * 返回值表示纳秒从一些固定但任意的时间（时间可能是未来的，所以返回值有可能是负数）得到
     *
     * md5 = hashlib.md5('adsf')
     * md5.digest() //返回: '\x05\xc1*(s48l\x94\x13\x1a\xb8\xaa\x00\xd0\x8a'    #二进制
     * md5.hexdigest() //返回: '05c12a287334386c94131ab8aa00d08a'     #十六进制
     */
    private String encodingFilename(String filename) {
        byte[] bytes = (filename + System.nanoTime()).getBytes();
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 检查文件是否符合条件
     * @param file 文件对象
     * @param fileExtension 文件扩展名
     */
    private void checkAllowed(MultipartFile file, String fileExtension) {
        String[] allowedSuffixNames = fileConfig.getAllowedSuffixNames();
        long fileSize = file.getSize(); //获取文件大小
        int filenameLength = file.getOriginalFilename().length(); //获取文件名长度
        // 文件内容不能为空
        if (file.isEmpty()){
            throw new RuntimeException("文件内容不能为空");
        }
        // 判断后缀名是否符合要求
        if (Arrays.binarySearch(allowedSuffixNames, fileExtension) == -1){
            throw new RuntimeException("不支持的文件类型" + fileExtension);
        }
        // 如果文件名大于指定个字符个数就抛异常
        if (filenameLength > fileConfig.getFilenameMaxLength()) {
            throw new RuntimeException("文件名长度超出限定长度");
        }
        // 如果文件大小超出指定大小就抛异常
        if (fileSize > fileConfig.getFileMaxSize()) {
            throw new RuntimeException("文件大小超出最大限定大小");
        }
    }
}