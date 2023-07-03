package com.bluedot.domain.file.model;

import com.bluedot.domain.file.AbstractDomainFile;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 0:22
 * @Description ：
 */
public class PythonFile extends AbstractDomainFile {

    public PythonFile(String fileId) {
        super(fileId);
    }

    @Override
    protected String generateName() {
        return "python_algorithm.py";
    }

    @Override
    protected String generatePath() {
        return "src/main/resources/algo/python/"+getFileId()+"/";
    }
}
