package com.anjaniy.marutinandan_restaurant_api.services;

import com.anjaniy.marutinandan_restaurant_api.models.dto.item.CreateItemRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.GetItemsRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.common.PaginatedResponse;
import com.anjaniy.marutinandan_restaurant_api.models.entities.Item;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.ItemDto;
import com.anjaniy.marutinandan_restaurant_api.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public int addItem(CreateItemRequest request) {
        try {
            return itemRepository.addItem(request);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return -1;
        }
    }

    public PaginatedResponse<Item> getItems(GetItemsRequest request) {
        try {
            return itemRepository.getItems(request);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new PaginatedResponse<>();
        }
    }

    public Item getItem(int id) {
        try {
            return id > 0 ? itemRepository.getItem(id) : null;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    public boolean deleteItem(int id) {
        try {
            return itemRepository.deleteItem(id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
    }

    public boolean updateItem (ItemDto itemDto) {
        try {
            return itemRepository.updateItem(itemDto);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
    }
}
