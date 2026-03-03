package com.coco.common.pagination;

import org.springframework.data.domain.PageRequest;

public class PageRequestDto {

    private int page;
    private int size;
    private String sort;

    public PageRequestDto(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(page, size);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
