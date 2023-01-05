package com.hotel.models.rooms;

import com.hotel.models.auth.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public abstract class Room {
    private String number;
    private Integer numberOfBeds;
    private Boolean isBooked = false;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User guest;

    public Room() {
    }

    public Room(String number, Integer numberOfBeds) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean booked) {
        isBooked = booked;
    }

    public void bookRoom(User guest) {
        this.guest = guest;
        isBooked = true;
    }

    public void openRoom() {
        this.guest = null;
        isBooked = false;
    }
}
