package com.anjaniy.marutinandan_restaurant_api.controllers;

import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.AddOrRenewMembershipRequest;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus;
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipType;
import com.anjaniy.marutinandan_restaurant_api.services.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/addOrRenew")
    public ResponseEntity<Map<String, Object>> addOrRenewMembership(@Valid @RequestBody AddOrRenewMembershipRequest request) {
        Map<String, Object> map = membershipService.addOrRenewMembership(request);
        if(map.get("membershipType").equals(MembershipType.RENEW)) {
            HttpStatus status = (boolean) map.get("updateStatus") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            String message = (boolean) map.get("updateStatus") ? "Membership renewed successfully" : "Something went wrong while renewing membership";
            return ResponseEntity.status(status).body(Map.of("updateStatus", map.get("updateStatus"), "message", message));
        } else {
            HttpStatus status = (int) map.get("id") > 0 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
            String message = (int) map.get("id") > 0 ? "Membership added successfully" : "Something went wrong while adding membership";
            return ResponseEntity.status(status).body(Map.of("id", map.get("id"), "message", message));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkMembership(@Valid @RequestBody CheckMembershipRequest request) {
        MembershipStatus membershipStatus = membershipService.checkMembership(request);
        HttpStatus status = membershipStatus.equals(MembershipStatus.ACTIVE) ? HttpStatus.OK : (membershipStatus.equals(MembershipStatus.EXPIRED)) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = membershipStatus.equals(MembershipStatus.ACTIVE) ? "Membership is active" : (membershipStatus.equals(MembershipStatus.EXPIRED)) ? "Membership is expired" : "No registered membership found";
        return ResponseEntity.status(status).body(Map.of("membershipStatus", membershipStatus.name(), "message", message));
    }
}
