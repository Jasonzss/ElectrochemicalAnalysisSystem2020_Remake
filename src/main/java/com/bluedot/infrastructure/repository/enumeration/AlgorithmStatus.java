package com.bluedot.infrastructure.repository.enumeration;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 16:39
 * @Description ：
 */
public enum AlgorithmStatus {
    /**
     * 表示算法所处于的管理状态
     */
    PRIVATE("未公开"),PUBLIC("公开"),UNDER_REVIEW("审核中"),DELETED("已删除");

    private String status;

    AlgorithmStatus(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
