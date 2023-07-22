package com.bluedot.domain.file.model;

import com.bluedot.domain.file.AbstractDomainFile;

/**
 * @author Jason
 * @creationDate 2023/07/21 - 16:36
 */
public class UserImageFile extends AbstractDomainFile {
    public UserImageFile(String fileId) {
        super(fileId);
    }

    @Override
    protected String generateName() {
        return getFileId()+".jpg";
    }

    @Override
    protected String generatePath() {
        return "web/user_img/";
    }

    /**
     * 获取文件在服务上的位置
     * @return 文件在服务器上的位置
     */
    public String getFileUri(){
        return "user_img/"+generateName();
    }
}
