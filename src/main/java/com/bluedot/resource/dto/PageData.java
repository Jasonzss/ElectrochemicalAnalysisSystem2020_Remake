package com.bluedot.resource.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
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

    /**
     * 当前页的所有数据
     */
    private List<T> data;

    private int totalPage;

    public static <T> PageData<T> of(Page<T> page){
        PageData<T> d = new PageData<>();
        d.setPageNumber(page.getNumber());
        d.setPageSize(page.getSize());
        d.setTotalPage(page.getTotalPages());
        d.setData(page.getContent());
        return d;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", data=" + data +
                ", totalPage=" + totalPage +
                '}';
    }
}
