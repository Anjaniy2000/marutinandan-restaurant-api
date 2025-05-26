package com.anjaniy.marutinandan_restaurant_api.controllers;

import com.anjaniy.marutinandan_restaurant_api.models.dto.item.GetItemsRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.common.PaginatedResponse;
import com.anjaniy.marutinandan_restaurant_api.models.entities.Item;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.ItemDto;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.CreateItemRequest;
import com.anjaniy.marutinandan_restaurant_api.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addItem(@Valid @RequestBody CreateItemRequest request) {
        int id = itemService.addItem(request);
        HttpStatus status = id > 0 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = id > 0 ? "Item created successfully" : "Something went wrong while creating an item";
        return ResponseEntity.status(status).body(Map.of("id", id, "message", message));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getItems(@Valid @RequestBody GetItemsRequest request) {
        PaginatedResponse<Item> items = itemService.getItems(request);
        HttpStatus status = items.getData().isEmpty() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        String message = items.getData().isEmpty() ? "Something went wrong while fetching the items" : "Available items";
        return ResponseEntity.status(status)
                .body(Map.of("items", items, "message", message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getItem(@PathVariable("id") int id) {
        Item item = itemService.getItem(id);
        HttpStatus status = item == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        String message = item == null ? "Something went wrong while fetching an item" : "Available item";
        return ResponseEntity.status(status)
                .body(Map.of("item", item == null ? Map.of() : item, "message", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable("id") int id) {
        boolean isDeleted = itemService.deleteItem(id);
        HttpStatus status = (isDeleted) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = (isDeleted) ? "Item deleted successfully" : "Something went wrong while deleting an item";
        return ResponseEntity.status(status)
                .body(Map.of("message", message));

    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updateItem(@Valid @RequestBody ItemDto itemDto) {
        boolean isUpdated = itemService.updateItem(itemDto);
        HttpStatus status = (isUpdated) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = (isUpdated) ? "Item updated successfully" : "Something went wrong while updating an item";
        return ResponseEntity.status(status)
                .body(Map.of("message", message));

    }

}
