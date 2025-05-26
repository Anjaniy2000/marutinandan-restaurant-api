package com.anjaniy.marutinandan_restaurant_api.models.entities;

import java.time.LocalDate;
import java.time.Period;

public class Membership {
    private int id;
    private String name;
    private String phone_number;
    private LocalDate registration_date;
    private LocalDate expiry_date;
    private Period duration;

    public Membership() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Period getDuration() {
        return duration;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", registration_date=" + registration_date +
                ", expiry_date=" + expiry_date +
                ", duration=" + duration +
                '}';
    }
}
