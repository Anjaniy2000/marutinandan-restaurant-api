package com.anjaniy.marutinandan_restaurant_api.services

import com.anjaniy.marutinandan_restaurant_api.BaseIntegrationSpec
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.CreateItemRequest
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.GetItemsRequest
import com.anjaniy.marutinandan_restaurant_api.models.dto.item.ItemDto

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

class ItemServiceSpec extends BaseIntegrationSpec {

    @Autowired
    private ItemService itemService

    def 'add item' () {
        given:
        CreateItemRequest request = new CreateItemRequest()
        request.name = 'Fried Rice'
        request.price = 120
        when:
        def result = itemService.addItem(request)
        then:
        assert result > 0
        cleanup:
        itemService.deleteItem(result)
    }

    @Sql(statements = [
            "INSERT INTO items (id, name, price) VALUES (111, 'Fried Rice', 120)"
    ])
    @Sql(statements = [
            "DELETE FROM items WHERE id = 111"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'get item' () {
        given:
        def id = 111
        when:
        def result = itemService.getItem(id)
        then:
        assert result.id == 111
        assert result.name == 'Fried Rice'
        assert result.price == 120.0
    }

    @Sql(statements = [
            "INSERT INTO items (id, name, price) VALUES (101, 'Fried Rice', 120)",
            "INSERT INTO items (id, name, price) VALUES (102, 'Paneer Butter Masala', 150)",
            "INSERT INTO items (id, name, price) VALUES (103, 'Veg Biryani', 130)",
            "INSERT INTO items (id, name, price) VALUES (104, 'Masala Dosa', 90)",
            "INSERT INTO items (id, name, price) VALUES (105, 'Chole Bhature', 110)"
    ])
    @Sql(statements = [
            "DELETE FROM items WHERE id IN (101, 102, 103, 104, 105)"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'get items' () {
        given:
        GetItemsRequest request = new GetItemsRequest()
        request.startPage = 0
        request.pageSize = 2
        when:
        def result = itemService.getItems(request)
        then:
        assert result.data.size() == 2
        assert result.data.get(0).name == 'Fried Rice'
        assert result.data.get(0).price == 120.0
        assert result.data.get(1).name == 'Paneer Butter Masala'
        assert result.data.get(1).price == 150.0
        assert result.totalCount == 5
        assert result.totalPages == 3
        assert result.hasNext
        assert !result.hasPrevious
        assert result.currentPage == 0
        assert result.nextPage == 1
    }

    @Sql(statements = [
            "INSERT INTO items (id, name, price) VALUES (222, 'Hakka Noodles', 90)"
    ])
    @Sql(statements = [
            "DELETE FROM items WHERE id = 222"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'update item' () {
        given:
        ItemDto itemDto = new ItemDto()
        itemDto.id = 222
        itemDto.name = 'Korean Noodles'
        itemDto.price = 150
        when:
        def result = itemService.updateItem(itemDto)
        then:
        assert result
    }


    @Sql(statements = [
            "INSERT INTO items (id, name, price) VALUES (333, 'Peas Rice', 100)"
    ])
    def 'delete item' () {
        given:
        def id = 333
        when:
        def result = itemService.deleteItem(id)
        then:
        assert result
    }

}
