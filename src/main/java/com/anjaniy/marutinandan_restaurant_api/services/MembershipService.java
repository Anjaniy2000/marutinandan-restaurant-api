package com.anjaniy.marutinandan_restaurant_api.services;

import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.AddOrRenewMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.entities.Membership;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipType;
import com.anjaniy.marutinandan_restaurant_api.repositories.MembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Optional;

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

            Optional<Membership> existing = membershipRepository.findByPhoneNumber(request.getPhone_number());

            if (request.getMembershipType() == MembershipType.RENEW && existing.isPresent()) {
                Membership membership = existing.get();
                membership.setCustomer_name(request.getCustomer_name());
                membership.setRegistration_date(request.getRegistration_date());
                membership.setExpiry_date(expiry_date);
                membership.setDuration(formatDuration(duration));
                membership.setMembershipStatus(MembershipStatus.ACTIVE);
                Membership updated = membershipRepository.save(membership);

                return Map.of("membershipType", MembershipType.RENEW, "id", updated.getId());
            } else {
                Membership membership = new Membership();
                membership.setCustomer_name(request.getCustomer_name());
                membership.setPhone_number(request.getPhone_number());
                membership.setRegistration_date(request.getRegistration_date());
                membership.setExpiry_date(expiry_date);
                membership.setDuration(formatDuration(duration));
                membership.setMembershipStatus(MembershipStatus.ACTIVE);

                Membership saved = membershipRepository.save(membership);
                return Map.of("membershipType", MembershipType.FRESH, "id", saved.getId());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Map.of();
        }
    }

    public MembershipStatus checkMembership(CheckMembershipRequest request) {
        try {
            return membershipRepository.findByPhoneNumber(request.getPhone_number())
                    .map(membership -> {
                        if (membership.getExpiry_date() == null || membership.getExpiry_date().isBefore(LocalDate.now())) {
                            membership.setMembershipStatus(MembershipStatus.EXPIRED);
                            membershipRepository.save(membership);
                            return MembershipStatus.EXPIRED;
                        }
                        return MembershipStatus.ACTIVE;
                    })
                    .orElse(MembershipStatus.UNREGISTERED);
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
