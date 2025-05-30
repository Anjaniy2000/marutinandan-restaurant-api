package com.anjaniy.marutinandan_restaurant_api.services

import com.anjaniy.marutinandan_restaurant_api.BaseIntegrationSpec
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.AddOrRenewMembershipRequest
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.time.Period

class MembershipServiceSpec extends BaseIntegrationSpec {

    @Autowired
    MembershipService membershipService

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate


    def 'add membership' () {
        given:
        AddOrRenewMembershipRequest request = new AddOrRenewMembershipRequest()
        request.phone_number = "8799535111"
        request.customer_name = "Anil Shah"
        request.registration_date = LocalDate.now()
        request.duration = "1y"
        request.membershipType = MembershipType.FRESH
        when:
        def result = membershipService.addOrRenewMembership(request)
        then:
        assert result.get("id") > 0
        assert result.get("membershipType") == MembershipType.FRESH
        cleanup:
        namedParameterJdbcTemplate.update("DELETE FROM memberships WHERE phone_number = :phone_number", Map.of("phone_number", request.phone_number))
    }

    @Sql(statements = [
            "INSERT INTO memberships (id, customer_name, phone_number, registration_date, expiry_date, duration, membership_status) VALUES (999, 'Sita Shah', '8799285110', '2024-11-01', '2025-04-01', '6 months', 'EXPIRED')"
    ])
    @Sql(statements = [
            "DELETE FROM memberships WHERE id = 999"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'renew membership' () {
        given:
        AddOrRenewMembershipRequest request = new AddOrRenewMembershipRequest()
        request.phone_number = "8799285110"
        request.customer_name = "Sita M. Shah"
        request.registration_date = LocalDate.now()
        request.duration = "6y"
        request.membershipType = MembershipType.RENEW
        when:
        def result = membershipService.addOrRenewMembership(request)
        then:
        assert result.get("id") > 0
        assert result.get("membershipType") == MembershipType.RENEW
    }

    @Sql(statements = [
            "INSERT INTO memberships (id, customer_name, phone_number, registration_date, expiry_date, duration, membership_status) VALUES (111, 'Jay Shah', '8799535110', '2025-01-01', '2025-06-06', '6 months', 'ACTIVE')"
    ])
    @Sql(statements = [
            "DELETE FROM memberships WHERE id = 111"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'check membership - active' () {
        given:
        CheckMembershipRequest request = new CheckMembershipRequest()
        request.phone_number = '8799535110'
        when:
        def result = membershipService.checkMembership(request)
        then:
        assert result == MembershipStatus.ACTIVE
    }

    @Sql(statements = [
            "INSERT INTO memberships (id, customer_name, phone_number, registration_date, expiry_date, duration, membership_status) VALUES (222, 'Aman Shah', '9898165698', '2024-11-01', '2025-04-01', '6 months', 'ACTIVE')"
    ])
    @Sql(statements = [
            "DELETE FROM memberships WHERE id = 222"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'check membership - expired' () {
        given:
        CheckMembershipRequest request = new CheckMembershipRequest()
        request.phone_number = '9898165698'
        when:
        def result = membershipService.checkMembership(request)
        then:
        assert result == MembershipStatus.EXPIRED
    }

    @Sql(statements = [
            "INSERT INTO memberships (id, customer_name, phone_number, registration_date, expiry_date, duration, membership_status) VALUES (333, 'gita Shah', '9898165090', '2024-11-01', '2025-04-01', '6 months', 'ACTIVE')"
    ])
    @Sql(statements = [
            "DELETE FROM memberships WHERE id = 333"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'check membership - unregistered' () {
        given:
        CheckMembershipRequest request = new CheckMembershipRequest()
        request.phone_number = '9898165698'
        when:
        def result = membershipService.checkMembership(request)
        then:
        assert result == MembershipStatus.UNREGISTERED
    }

    def 'parse duration' () {
        given:
        def duration = '1y'
        when:
        def result = membershipService.parseDuration(duration)
        then:
        assert result == Period.of(1,0,0)
    }

    def 'format duration' () {
        given:
        def duration = Period.of(0,6,0)
        when:
        def result = membershipService.formatDuration(duration)
        then:
        assert result == '6 months'
    }


}
