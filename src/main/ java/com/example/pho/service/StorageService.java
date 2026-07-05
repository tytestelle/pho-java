
package com.example.pho.service;

import java.io.InputStream;
import java.util.List;

public interface StorageService {

    /**
     * 列出指定路径下的所有文件（相对路径）
     */
    List<String> listFiles(String path);

    /**
     * 上传文件流到目标路径
     */
    void uploadFile(String targetPath, InputStream inputStream);

    /**
     * 下载文件流
     */
    InputStream downloadFile(String path);

    /**
     * 检查文件是否存在
     */
    boolean exists(String path);

    /**
     * 删除文件
     */
    void deleteFile(String path);
}
