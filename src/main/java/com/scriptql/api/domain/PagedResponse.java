package com.scriptql.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PagedResponse<T> {

    private List<T> items;

    private int page;
    private int limit;
    private boolean last;
    private long total;
    private long pages;

    public PagedResponse(Page<T> page) {
        this.page = page.getNumber() + 1;
        this.limit = page.getPageable().getPageSize();
        this.last = page.isLast();
        this.items = page.getContent();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
    }

    public void loadFrom(Page<?> page, List<T> items) {
        this.page = page.getNumber() + 1;
        this.limit = page.getPageable().getPageSize();
        this.last = page.isLast();
        this.items = items;
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
    }

    public static <T> PagedResponse<T> ofUnpaged(List<T> items) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setItems(items);
        response.setPage(0);
        response.setLast(true);
        response.setLimit(999);
        return response;
    }

}
