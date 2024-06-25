package com.synergisticit.AirlinesReservation.domain;

import java.util.ArrayList;
import java.util.List;

// Helper object for pagination control
public class PageInfo {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortOrder;
    private int[] pages;
    private int totalPages;

    public PageInfo() {
    }

    public PageInfo(int pageNo, int pageSize, String sortBy, String sortOrder) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }
    public void setPages() {
        List<Integer> pages = new ArrayList<>();
        for(int i = 0; i < totalPages; i++) {
            pages.add(i);
        }
        this.pages = new int[pages.size()];
        for(int i = 0; i < pages.size(); i++) {
            this.pages[i] = pages.get(i);
        }

    }
    public int[] getPages() {
        setTotalPages(totalPages);
        return pages;
    }
    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void toggleSortOrder() {
        if(this.sortOrder.equals("asc")) {
            this.sortOrder = "desc";
        } else {
            this.sortOrder = "asc";
        }

    }

    public int getTotalPages() {

        return totalPages;
    }

    public void setTotalPages(int totalPages) {

        this.totalPages = totalPages;
    }
}
