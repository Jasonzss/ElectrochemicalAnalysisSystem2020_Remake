package com.bluedot.infrastructure.repository.enumeration;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 15:53
 */
public enum UserStatus {
    /**
     * 表示算法所处于的管理状态
     */
    NORM("正常",""),
    FREEZE("冻结","执行非法操作"),
    LOGIN_FREEZE("冻结","异常登录操作"),
    UNFREEZING("冻结中","等待审核员审核解冻申请"),
    DELETED("已注销","");

    private String status;
    private String desc;

    UserStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
