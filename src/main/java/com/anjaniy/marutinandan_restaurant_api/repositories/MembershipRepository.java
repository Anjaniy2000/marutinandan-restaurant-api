package com.anjaniy.marutinandan_restaurant_api.repositories;

import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Map;

@Repository
public class MembershipRepository {

    private static final String MEMBERSHIP_TABLE = "membership";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MembershipRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Map<String, Object> addOrRenewMembership(String name, String phoneNumber, LocalDate registrationDate, LocalDate expiryDate, String duration, MembershipType membershipType) {
        StringBuilder query;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("phone_number", phoneNumber)
                .addValue("registration_date", registrationDate)
                .addValue("expiry_date", expiryDate)
                .addValue("duration", duration);

        if (membershipType == MembershipType.RENEW) {
            params.addValue("membership_status", MembershipStatus.ACTIVE.name());
            query = new StringBuilder("UPDATE ")
                    .append(MEMBERSHIP_TABLE)
                    .append(" SET name = :name, registration_date = :registration_date, expiry_date = :expiry_date, duration = :duration, membership_status = :membership_status")
                    .append(" WHERE phone_number = :phone_number");

            return Map.of("membershipType", MembershipType.RENEW, "updateStatus", namedParameterJdbcTemplate.update(query.toString(), params) > 0);
        } else {
            query = new StringBuilder("INSERT INTO ")
                    .append(MEMBERSHIP_TABLE)
                    .append("(name, phone_number, registration_date, expiry_date, duration)")
                    .append(" VALUES(:name, :phone_number, :registration_date, :expiry_date, :duration)");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(query.toString(), params, keyHolder, new String[]{"id"});
            return Map.of("membershipType", MembershipType.FRESH, "id", keyHolder.getKey().intValue() > 0 ? keyHolder.getKey().intValue() : -1);
        }
    }


    public MembershipStatus checkMembership(CheckMembershipRequest request) {
        StringBuilder query = new StringBuilder("SELECT expiry_date FROM ")
                .append(MEMBERSHIP_TABLE)
                .append(" WHERE phone_number = :phone_number");

        try {
            LocalDate expiry_date = namedParameterJdbcTemplate.queryForObject(query.toString(), Map.of("phone_number", request.getPhone_number()), LocalDate.class);
            if (expiry_date == null) {
                return MembershipStatus.UNREGISTERED;
            }
            if(!expiry_date.isAfter(LocalDate.now())) {
                updateMembershipStatus(request.getPhone_number());
                return MembershipStatus.EXPIRED;
            }
            return MembershipStatus.ACTIVE;
        } catch (EmptyResultDataAccessException ex) {
            // No record found
            return MembershipStatus.UNREGISTERED;
        }
    }

    private void updateMembershipStatus(String phone_number) {
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(MEMBERSHIP_TABLE)
                .append(" SET membership_status = 'EXPIRED'")
                .append(" WHERE phone_number = :phone_number")
                .append(" AND membership_status != 'EXPIRED'");
        namedParameterJdbcTemplate.update(query.toString(), Map.of("phone_number", phone_number));
    }

}
