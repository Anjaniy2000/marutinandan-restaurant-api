package com.anjaniy.marutinandan_restaurant_api.repositories;

import com.anjaniy.marutinandan_restaurant_api.models.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    @Query("SELECT m FROM Membership m WHERE m.phone_number = :phone_number")
    Optional<Membership> findByPhoneNumber(@Param("phone_number") String phone_number);
}
