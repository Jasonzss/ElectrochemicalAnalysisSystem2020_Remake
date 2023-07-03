package com.bluedot.domain.file;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Collections;

/**
 * 业务模型文件的顶级抽象
 *
 * <p>代表着该文件在对应业务领域中的管理逻辑，比如java文件在java业务中的需要插入package信息之类的。
 * 这些都应该是在file模块完成的功能，帮助上层算法层解决一些无关算法的逻辑
 *
 * <p>除此之外还包括文件的其他基本功能，例如增删改查
 *
 * @author Jason
 * @creationDate 2023/06/25 - 18:53
 */
public interface DomainFile {
    /**
     * 获取领域模型文件的id
     * @return 领域模型文件id
     */
    String getFileId();

    /**
     * 获取领域文件本体
     * @return 领域模型文件
     */
    InputStream getInputStream() throws FileNotFoundException;

    /**
     * 删除领域模型文件
     * @return 是否删除成功
     */
    boolean deleteFile();

    /**
     * 修改文件内容
     * @param is 修改后的文件内容
     * @return 修改后有多少个字符
     */
    long updateFile(InputStream is);


}
