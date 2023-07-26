package com.bluedot.resource.vo;

import cn.hutool.core.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.ws.rs.QueryParam;

/**
 * 改编自 {@link cn.hutool.db.Page}
 *
 * @author Jason
 * @creationDate 2023/07/23 - 23:11
 *
 * 目前仅支持单字段排序
 */
public class PageInfo {
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 页码，0表示第一页
     */
    @QueryParam("page")
    private int pageNumber;
    /**
     * 每页结果数
     */
    @QueryParam("limit")
    private int pageSize;

    /**
     * 排序字段
     */
    @QueryParam("sort")
    private String sort;

    /**
     * 排序方式（正序还是反序）
     */
    @QueryParam("direction")
    private String direction;

    public static PageInfo of(int pageNumber, int pageSize) {
        return new PageInfo(pageNumber, pageSize);
    }

    // ---------------------------------------------------------- Constructor start

    /**
     * 构造，默认第0页，每页{@value #DEFAULT_PAGE_SIZE} 条
     *
     * @since 4.5.16
     */
    public PageInfo() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，0表示第一页
     * @param pageSize   每页结果数
     */
    public PageInfo(int pageNumber, int pageSize) {
        this.pageNumber = Math.max(pageNumber, 0);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，0表示第一页
     * @param pageSize 每页结果数
     * @param sort 排序字段
     * @param direction 正序 或 反序
     */
    public PageInfo(int pageNumber, int pageSize, String sort, String direction) {
        this(pageNumber, pageSize);
        this.sort = sort;
        this.direction = direction;
    }
    // ---------------------------------------------------------- Constructor start

    // ---------------------------------------------------------- Getters and Setters start

    /**
     * @return 页码，0表示第一页
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页码，0表示第一页
     *
     * @param pageNumber 页码
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, 0);
    }

    /**
     * @return 每页结果数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页结果数
     *
     * @param pageSize 每页结果数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return 开始位置
     * @see #getStartIndex()
     */
    public int getStartPosition() {
        return getStartIndex();
    }

    public Integer getStartIndex() {
        return PageUtil.getStart(this.pageNumber, this.pageSize);
    }

    /**
     * @return 结束位置
     * @see #getEndIndex()
     */
    public int getEndPosition() {
        return getEndIndex();
    }

    public Integer getEndIndex() {
        return PageUtil.getEnd(this.pageNumber, this.pageSize);
    }

    // ---------------------------------------------------------- Getters and Setters end

    /**
     * 开始位置和结束位置<br>
     * 例如：
     *
     * <pre>
     * 页码：0，每页10 =》 [0, 10]
     * 页码：1，每页10 =》 [10, 20]
     * 页码：2，每页10 =》 [21, 30]
     * 。。。
     * </pre>
     *
     * @return 第一个数为开始位置，第二个数为结束位置
     */
    public int[] getStartEnd() {
        return PageUtil.transToStartEnd(pageNumber, pageSize);
    }

    public Pageable getPageable(){
        if(StringUtils.isEmpty(sort)){
            return PageRequest.of(pageNumber, pageSize);
        }else {
            if (StringUtils.isEmpty(direction)){
                direction = Sort.Direction.ASC.toString();
            }
            return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.valueOf(direction),sort));
        }
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
