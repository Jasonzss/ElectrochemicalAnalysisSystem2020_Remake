package com.bluedot.domain.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:10
 * @Description ：文件功能的api，负责对java算法文件、python算法文件、静态文件、日志文件、实验数据文件等文件的管理
 * 管理内容包括增删改查
 * java文件的编译
 * 文件目录的自动管理管理
 */
public interface FileFactory {
    public enum FileTypes{
        /**
         * 所有支持操作的文件类型，class文件为自动管理，用户无法操作。
         */
        JAVA,PYTHON,DATA,LOG
    }

    /**
     * 获取（业务模型）文件
     * @param fileId 文件id，同时也是物理文件名（不包括后缀）
     * @param fileType 业务文件的类型
     * @param <T> 业务模型文件
     * @return 业务模型文件
     * @throws FileNotFoundException 找不到源文件
     */
    <T extends File> T getModelFile(String fileId,Class<T> fileType) throws FileNotFoundException;

    /**
     * 根据文件id和文件类型获取业务文件模型
     * @param fileId 文件id，同时也是物理文件名（不包括后缀）
     * @param fileType 文件类型
     * @param <T> 业务模型文件
     * @return 业务模型文件
     * @throws FileNotFoundException 找不到源文件
     */
    <T extends File> T getModelFile(String fileId,FileTypes fileType) throws FileNotFoundException;

    /**
     * 不存在则创建该文件，默认为空文件
     * @param fileId 文件id
     * @param fileTypes 文件类型枚举类
     * @param <T> 文件类型
     * @return 业务模型文件
     */
    <T extends File> T createIfAbsent(String fileId, FileTypes fileTypes);

    /**
     *
     * @param fileId
     * @param fileTypes
     * @param inputStream
     * @param <T>
     * @return
     */
    <T extends File> T createIfAbsent(String fileId, FileTypes fileTypes, InputStream inputStream);
}
