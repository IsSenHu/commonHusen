package com.husen.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/11/5 20:03.
 */
public class TableData<T> implements Serializable {
    private static final long serialVersionUID = 7310832027600876171L;

    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totals;
    private List<T> content;

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Long getTotals() {
        return totals;
    }

    public List<T> getContent() {
        return content;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotals(Long totals) {
        this.totals = totals;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TableData{" +
                "page=" + page +
                ", size=" + size +
                ", totalPages=" + totalPages +
                ", totals=" + totals +
                ", content=" + content +
                '}';
    }
}
