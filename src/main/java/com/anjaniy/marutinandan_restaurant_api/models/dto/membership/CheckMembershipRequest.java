package com.anjaniy.marutinandan_restaurant_api.models.dto.membership;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CheckMembershipRequest {
    @NotBlank(message = "phone_number is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "phone_number must be exactly 10 digits with no special characters"
    )
    private String phone_number;

    public CheckMembershipRequest() {

    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "CheckMembershipRequest{" +
                "phone_number='" + phone_number + '\'' +
                '}';
    }
}
