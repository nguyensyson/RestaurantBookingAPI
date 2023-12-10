package com.poly.bookingapi.dto;


public class PageModel {
    private Integer page;
    private Integer size;

    public PageModel() {
    }

    public PageModel(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        if (page == null)
            return 0;
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
