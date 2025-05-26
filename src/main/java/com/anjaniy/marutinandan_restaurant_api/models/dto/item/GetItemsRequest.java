package com.anjaniy.marutinandan_restaurant_api.models.dto.item;

import jakarta.validation.constraints.Min;

public class GetItemsRequest {

    @Min(value = 0, message = "startPage must be greater than or equal to 0")
    private int startPage;

    @Min(value = 1, message = "pageSize must be greater than 0")
    private int pageSize;

    public GetItemsRequest() {
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "GetUsersRequest{" +
                "startPage=" + startPage +
                ", pageSize=" + pageSize +
                '}';
    }

}

