package com.example.pho.sync;

import com.example.pho.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncScheduler.class);

    @Autowired
    private StorageService storageService;

    /**
     * 每分钟执行一次同步任务（示例）
     */
    @Scheduled(fixedDelay = 60000)
    public void sync() {
        log.info("Starting sync task...");
        try {
            // 这里实现你的扫描、比对、上传逻辑
            // 目前仅打印存储服务中的文件列表
            var files = storageService.listFiles("/");
            log.info("Found {} files in root", files.size());
            // 在此添加实际的同步代码
        } catch (Exception e) {
            log.error("Sync task failed", e);
        }
    }
}
