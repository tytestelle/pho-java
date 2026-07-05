# pho-java

Java 重写版 Pho 照片同步工具（骨架项目）。

## 快速开始

1. 确保已安装 JDK 21 和 Maven
2. 运行 `mvn spring-boot:run`
3. 访问 `http://localhost:8080/api/photos/list` 查看根目录文件列表
4. 上传文件：POST `/api/photos/upload` 带上 `file` 和 `path` 参数

## 扩展开发

- 实现 `StorageService` 接口添加新的存储后端（Samba, WebDAV, 网盘等）
- 在 `SyncScheduler.sync()` 中编写真正的同步逻辑
- 可增加元数据库管理（JPA + H2）

## 自动构建

推送到 GitHub 后，Actions 会自动构建并生成 JAR 包和 Docker 镜像。
