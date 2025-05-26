package com.anjaniy.marutinandan_restaurant_api.models.dto.item;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ItemDto {
    @Min(value = 1, message = "Item id must be greater than 0")
    private int id;

    @NotBlank(message = "Item name is required")
    private String name;
    @DecimalMin(value = "0.01", inclusive = true, message = "Item price must be greater than 0")
    private float price;

    public ItemDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "ItemDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
