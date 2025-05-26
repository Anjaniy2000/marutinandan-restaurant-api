package com.anjaniy.marutinandan_restaurant_api.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MembershipScheduler {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipScheduler.class);

    private static final String MEMBERSHIP_TABLE = "membership";

    @Value("${membership.scheduler.enabled:true}")
    private boolean schedulerEnabled;

    public MembershipScheduler(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void updateExpiredMemberships() {
        if (!schedulerEnabled) return;

        try {
            StringBuilder query = new StringBuilder("UPDATE ")
                    .append(MEMBERSHIP_TABLE)
                    .append(" SET membership_status = 'EXPIRED")
                    .append(" WHERE expiry_date < CURRENT_DATE")
                    .append(" AND membership_status = 'ACTIVE'");

            int updatedRows = namedParameterJdbcTemplate.update(query.toString(), Map.of());
            LOGGER.info("Expired memberships updated: {}", updatedRows);
        } catch (Exception e) {
            LOGGER.error("Error updating expired memberships: {}", e.getMessage());
        }
    }
}
