package com.anjaniy.marutinandan_restaurant_api.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20)
    @NotBlank
    private String customer_name;
    @Column(nullable = false, length = 15, unique = true)
    private String phone_number;
    @Column(nullable = false)
    private LocalDate registration_date;
    @Column(nullable = false)
    private LocalDate expiry_date;
    @Column(nullable = false)
    @NotBlank
    private String duration;
    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status")
    private MembershipStatus membershipStatus;

    public Membership() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public LocalDate getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDate expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", customer_name='" + customer_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", registration_date=" + registration_date +
                ", expiry_date=" + expiry_date +
                ", duration='" + duration + '\'' +
                ", membershipStatus=" + membershipStatus +
                '}';
    }
}
