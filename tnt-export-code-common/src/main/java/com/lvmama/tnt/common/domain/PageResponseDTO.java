package com.lvmama.tnt.common.domain;

/**
 *
 */
public class PageResponseDTO<T> extends ResponseDTO {

    private static final long serialVersionUID = 1652725150938620727L;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalCount;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
