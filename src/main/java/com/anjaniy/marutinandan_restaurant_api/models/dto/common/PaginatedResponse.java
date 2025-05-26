package com.anjaniy.marutinandan_restaurant_api.models.dto.common;

import java.util.List;

public class PaginatedResponse <T> {
    private List<T> data;
    private int totalCount;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private int currentPage;
    private int nextPage;

    public PaginatedResponse() {

    }

    public PaginatedResponse(List<T> data, int totalCount, int totalPages, boolean hasNext, boolean hasPrevious, int currentPage, int nextPage) {
        this.data = data;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "PaginatedResponse{" +
                "data=" + data +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                ", currentPage=" + currentPage +
                ", nextPage=" + nextPage +
                '}';
    }
}
