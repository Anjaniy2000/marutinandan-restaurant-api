package com.anjaniy.marutinandan_restaurant_api.services

import com.anjaniy.marutinandan_restaurant_api.BaseIntegrationSpec
import com.anjaniy.marutinandan_restaurant_api.models.dto.membership.CheckMembershipRequest
import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

import java.time.Period

class MembershipServiceSpec extends BaseIntegrationSpec {

    @Autowired
    MembershipService membershipService

    @Sql(statements = [
            "INSERT INTO membership (id, customer_name, phone_number, registration_date, expiry_date, duration, membership_status) VALUES (111, 'Jay Shah', '8799535110', '2025-01-01', '2025-06-06', '6 months', 'ACTIVE')"
    ])
    @Sql(statements = [
            "DELETE FROM membership WHERE id = 111"
    ], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    def 'check membership' () {
        given:
        CheckMembershipRequest request = new CheckMembershipRequest()
        request.phone_number = '8799535110'
        when:
        def result = membershipService.checkMembership(request)
        then:
        assert result == MembershipStatus.ACTIVE
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
