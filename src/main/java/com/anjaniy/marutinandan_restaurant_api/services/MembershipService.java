package com.anjaniy.marutinandan_restaurant_api.services;

import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.AddOrRenewMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus;
import com.anjaniy.marutinandan_restaurant_api.repositories.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

@Service
public class MembershipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MembershipService.class);

    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public Map<String, Object> addOrRenewMembership(AddOrRenewMembershipRequest request) {
        try {
            Period duration = parseDuration(request.getDuration());
            LocalDate expiry_date = request.getRegistration_date().plus(duration);
            return membershipRepository.addOrRenewMembership(request.getName(), request.getPhone_number(), request.getRegistration_date(), expiry_date, formatDuration(duration), request.getMembershipType());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Map.of();
        }
    }

    public MembershipStatus checkMembership(CheckMembershipRequest request) {
        try {
            return membershipRepository.checkMembership(request);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return MembershipStatus.UNREGISTERED;
        }
    }

    private Period parseDuration(String duration) {
        return switch (duration) {
            case "6m" -> Period.ofMonths(6);
            case "1y" -> Period.ofYears(1);
            default -> Period.ZERO;
        };
    }

    private String formatDuration(Period period) {
        return period.getYears() > 0 ? period.getYears() + " year" : period.getMonths() + " months";
    }


}
