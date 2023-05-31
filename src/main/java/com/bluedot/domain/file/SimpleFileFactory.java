package com.bluedot.domain.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 0:37
 * @Description ：
 */
public class SimpleFileFactory implements FileFactory {
    /**
     * 项目根路径
     */
    private static final String BASE_PATH =
            Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();


    @Override
    public <T extends File> T getModelFile(String fileId, Class<T> fileType) throws FileNotFoundException {
        return null;
    }

    @Override
    public <T extends File> T getModelFile(String fileId, FileTypes fileType) throws FileNotFoundException {
        return null;
    }

    @Override
    public <T extends File> T createIfAbsent(String fileId, FileTypes fileTypes) {
        return null;
    }

    @Override
    public <T extends File> T createIfAbsent(String fileId, FileTypes fileTypes, InputStream inputStream) {
        return null;
    }
}
