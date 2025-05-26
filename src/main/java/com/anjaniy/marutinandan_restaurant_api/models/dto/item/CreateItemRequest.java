package com.anjaniy.marutinandan_restaurant_api.models.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class CreateItemRequest {
    @NotBlank(message = "Item name is required")
    private String name;
    @DecimalMin(value = "0.01", inclusive = true, message = "Item price must be greater than 0")
    private float price;

    public CreateItemRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
