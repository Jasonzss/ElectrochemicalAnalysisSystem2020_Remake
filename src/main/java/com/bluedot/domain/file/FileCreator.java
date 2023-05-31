package com.bluedot.domain.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/05/31 - 0:38
 * @Description ：
 */
public class FileCreator {
    public static final Logger log = LoggerFactory.getLogger(FileCreator.class);

    /**
     * 创建文件目录和文件
     * TODO 使用事务，在文件创建失败后撤回对目录的创建
     * @param file 被创建的文件
     * @return 文件是否被创建成功
     */
    public boolean createFile(AbstractDomainModelFile file){
        //先创建目录
        String filename = file.getAbsolutePath();
        String dirPath = filename.substring(0, filename.lastIndexOf('\\'));
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdir()) {
                log.info("dir create success:"+dir.getPath());
            }
        }

        try {
            //文件存在则删除，重新创建
            if (file.exists()) {
                if (file.delete()){
                    log.info("file delete success:"+file.getPath());
                }
            }
            if (file.createNewFile()) {
                log.info("file create success:"+file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists();
    }
}
