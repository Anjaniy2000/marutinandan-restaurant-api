package com.anjaniy.marutinandan_restaurant_api.repositories;

import com.anjaniy.marutinandan_restaurant_api.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
