package com.anjaniy.marutinandan_restaurant_api.services;

import com.anjaniy.marutinandan_restaurant_api.models.dto.item.CreateItemRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.GetItemsRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.common.PaginatedResponse;
import com.anjaniy.marutinandan_restaurant_api.models.entities.Item;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.ItemDto;
import com.anjaniy.marutinandan_restaurant_api.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            return itemRepository.save(new Item.Builder().name(request.getName()).price(request.getPrice()).build()).getId();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return -1;
        }
    }

    public PaginatedResponse<Item> getItems(GetItemsRequest request) {
        try {
//            int pageSize = Math.min(Math.max(request.getPageSize(), 10), 100);
            Pageable pageable = PageRequest.of(request.getStartPage(), request.getPageSize());
            Page<Item> items = itemRepository.findAll(pageable);
            PaginatedResponse<Item> response = new PaginatedResponse<>();
            response.setData(items.getContent());
            response.setHasNext(items.hasNext());
            response.setHasPrevious(items.hasPrevious());
            response.setNextPage(items.getPageable().next().getPageNumber());
            response.setTotalPages(items.getTotalPages());
            response.setTotalCount((int) items.getTotalElements());
            return response;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new PaginatedResponse<>();
        }
    }

    public Item getItem(int id) {
        try {
            return (id > 0) ? itemRepository.findById(id).orElse(null) : null;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    public boolean deleteItem(int id) {
        try {
            if(id > 0) {
                itemRepository.deleteById(id);
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
        return false;
    }

    public boolean updateItem (ItemDto itemDto) {
        try {
            itemRepository.save(new Item.Builder().id(itemDto.getId()).name(itemDto.getName()).price(itemDto.getPrice()).build());
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return false;
        }
    }

}
