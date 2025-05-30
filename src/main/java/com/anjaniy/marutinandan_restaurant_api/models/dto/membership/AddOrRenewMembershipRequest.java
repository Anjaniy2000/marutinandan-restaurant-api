package com.anjaniy.marutinandan_restaurant_api.models.dto.membership;

import com.anjaniy.marutinandan_restaurant_api.models.entities.MembershipType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class AddOrRenewMembershipRequest {

    @NotBlank(message = "customer_name is required")
    private String customer_name;

    @NotNull(message = "membership_type is required")
    private MembershipType membershipType;

    @NotBlank(message = "phone_number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "phone_number must be exactly 10 digits with no special characters"
    )
    private String phone_number;

    @NotNull(message = "registration_date is required")
    private LocalDate registration_date;

    @NotBlank(message = "duration is required")
    @Pattern(
            regexp = "^(6m|1y)$",
            message = "Duration must be either '6m' (6 months) or '1y' (1 year)"
    )
    private String duration;

    // ✅ Validate that registration date must be today's date
    @AssertTrue(message = "registration_date must be today's date")
    public boolean isRegistrationDateToday() {
        return registration_date.equals(LocalDate.now());
    }

    public AddOrRenewMembershipRequest(){

    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "AddOrRenewMembershipRequest{" +
                "customer_name='" + customer_name + '\'' +
                ", membershipType=" + membershipType +
                ", phone_number='" + phone_number + '\'' +
                ", registration_date=" + registration_date +
                ", duration='" + duration + '\'' +
                '}';
    }
}
