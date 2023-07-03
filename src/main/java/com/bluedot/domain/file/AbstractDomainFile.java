package com.bluedot.domain.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author Jason
 * @CreationDate 2023/05/31 - 0:41
 * @Description ：领域模型文件，指领域业务上和文件相关的的实体类，可以理解成持久化文件在Java程序业务中的映射类
 * 持久化文件 <-> 文件管理系统模型文件
 * 该类有基于JDK的File类有着一系列基础的文件操作功能，以及部分业务功能
 *
 * 业务功能包括
 *
 * 备份&还原
 *
 * TODO 并发操作下可能会出现文件操作冲突，这个问题需要解决 IORuntimeException: 另一个程序正在使用此文件，进程无法访问。
 *
 */
public abstract class AbstractDomainFile implements DomainFile {
    private static final Logger log = LoggerFactory.getLogger(AbstractDomainFile.class);
    /**
     * 领域文件本体
     */
    private File file;
    /**
     * 领域模型文件id
     */
    private final String fileId;

    public AbstractDomainFile(String fileId) {
        this.fileId = fileId;
        File file = new File(generatePath() + generateName());
        this.setFile(file);

    }

    protected File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public String getFileId() {
        return fileId;
    }

    /**
     * 默认只删除目标文件，如果需要改动其他东西子类可以重写doDelete
     * @return 是否有文件被删除
     */
    @Override
    public boolean deleteFile() {
        doDelete();
        return FileUtil.del(file);
    }

    @Override
    public long updateFile(InputStream is) {
        InputStream inputStream = doUpdate(is);

        try {
            File touch = FileUtil.touch(file);
            return IoUtil.copy(inputStream, new FileOutputStream(touch));
        } catch (FileNotFoundException e) {
            throw new FileSystemException(e, CommonErrorCode.E_2002);
        }
    }

    /**
     * 用于子类重写的删除方法额外逻辑
     */
    protected void doDelete(){

    }

    /**
     * 用于子类重写的修改方法额外逻辑
     * @return 对修改后的输入流进行处理
     */
    protected InputStream doUpdate(InputStream is){
        return is;
    }

    /**
     * 获取文件类型的后缀
     * @return 文件后缀
     */
    protected abstract String generateName();

    protected abstract String generatePath();

    public long lastModified(){
        return getFile().lastModified();
    }
}
