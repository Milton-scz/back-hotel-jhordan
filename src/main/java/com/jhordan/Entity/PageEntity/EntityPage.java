package com.jhordan.Entity.PageEntity;

import java.util.List;

public class EntityPage<T> {
    private PageInfo pageInfo;
    private List<T> items;

    public EntityPage(PageInfo pageInfo, List<T> items) {
        this.pageInfo = pageInfo;
        this.items = items;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}