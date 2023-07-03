package com.bluedot.domain.file.model;

import com.bluedot.domain.file.AbstractDomainFile;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 0:23
 * @Description ：
 */
public class DataFile extends AbstractDomainFile {
    public DataFile(String fileId) {
        super(fileId);
    }

    @Override
    protected String generateName() {
        return null;
    }

    @Override
    protected String generatePath() {
        return null;
    }
}
