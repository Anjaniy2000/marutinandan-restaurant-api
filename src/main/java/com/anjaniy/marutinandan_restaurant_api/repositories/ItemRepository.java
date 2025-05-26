package com.anjaniy.marutinandan_restaurant_api.repositories;

import com.anjaniy.marutinandan_restaurant_api.models.dto.item.CreateItemRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.GetItemsRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.common.PaginatedResponse;
import com.anjaniy.marutinandan_restaurant_api.models.entities.Item;
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.ItemDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final String ITEMS_TABLE = "item";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int addItem(CreateItemRequest request) {
        StringBuilder query = new StringBuilder("INSERT INTO ")
                .append(ITEMS_TABLE)
                .append(" (name, price) VALUES (:name, :price)");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", request.getName())
                .addValue("price", request.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(query.toString(), params, keyHolder, new String[] {"id"});

        return keyHolder.getKey().intValue() > 0 ? keyHolder.getKey().intValue() : -1;
    }

    public PaginatedResponse<Item> getItems(GetItemsRequest request) {
        int pageSize = request.getPageSize();
        int currentPage = request.getStartPage();
        int offset = currentPage * pageSize;

        StringBuilder query = new StringBuilder("SELECT * FROM ")
                .append(ITEMS_TABLE)
                .append(" LIMIT :limit OFFSET :offset");

        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM ")
                .append(ITEMS_TABLE);

        List<Item> items = namedParameterJdbcTemplate.query(query.toString(), Map.of("limit", pageSize, "offset", offset), (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getFloat("price"));
            return item;
        });

        int totalCount = namedParameterJdbcTemplate.queryForObject(countQuery.toString(), Collections.emptyMap(), Integer.class);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        return new PaginatedResponse<>(
                items,
                totalCount,
                totalPages,
                currentPage < totalPages - 1,
                currentPage > 0,
                currentPage,
                currentPage < totalPages - 1 ? currentPage + 1 : -1
        );
    }

    public Item getItem(int id) {
        StringBuilder query = new StringBuilder("SELECT * FROM ")
                .append(ITEMS_TABLE)
                .append(" WHERE id = :id");

        return namedParameterJdbcTemplate.queryForObject(query.toString(), Map.of("id", id), (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setPrice(rs.getFloat("price"));
            return item;
        });
    }

    public boolean deleteItem(int id) {
        StringBuilder query = new StringBuilder("DELETE FROM ")
                .append(ITEMS_TABLE)
                .append(" WHERE id = :id");

        return namedParameterJdbcTemplate.update(query.toString(), Map.of("id", id)) > 0;
    }

    public boolean updateItem(ItemDto itemDto) {
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(ITEMS_TABLE)
                .append(" SET name = :name, price = :price WHERE id = :id");

        Map<String, Object> params = Map.of(
                "id", itemDto.getId(),
                "name", itemDto.getName(),
                "price", itemDto.getPrice()
        );

        return namedParameterJdbcTemplate.update(query.toString(), params) > 0;
    }
}
