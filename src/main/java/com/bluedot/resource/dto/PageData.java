package com.bluedot.resource.dto;/**
 *
 * @author Jason
 * @creationDate 2023/07/23 - 23:47
 */
public class PageData<T> {
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 页码，0表示第一页
     */
    private int pageNumber;
    /**
     * 每页结果数
     */
    private int pageSize;
}
