package com.bluedot.domain.file;

import java.io.File;
import java.net.URI;

/**
 * @Author Jason
 * @CreationDate 2023/05/31 - 0:41
 * @Description ：领域模型文件，指领域业务上和文件相关的的实体类，可以理解成持久化文件在Java中的映射类
 * 该类有基于JDK的File类有着一系列基础的文件操作功能，以及部分业务功能
 *
 * 业务功能包括
 *
 * 备份&还原
 *
 */
public abstract class AbstractDomainModelFile extends File {

    public AbstractDomainModelFile(String pathname) {
        super(pathname);
    }

    public AbstractDomainModelFile(String parent, String child) {
        super(parent, child);
    }

    public AbstractDomainModelFile(File parent, String child) {
        super(parent, child);
    }

    public AbstractDomainModelFile(URI uri) {
        super(uri);
    }

    /**
     * 获取文件类型的后缀
     * @return 文件后缀
     */
    protected abstract String getSuffix();

//    protected abstract String getFileId();
}
