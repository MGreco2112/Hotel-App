package com.hotel.payloads.request;

import com.hotel.models.Hotel;
import com.hotel.models.auth.User;

public class UpdateRoomRequest {

    private String number;
    private Integer numberOfBeds;
    private String roomType;
    private Boolean isBooked;
    private Hotel hotel;
    private User guest;

    public UpdateRoomRequest() {
    }

    public UpdateRoomRequest(String number, Integer numberOfBeds, String roomType, Boolean isBooked, Hotel hotel, User guest) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
        this.roomType = roomType;
        this.isBooked = isBooked;
        this.hotel = hotel;
        this.guest = guest;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean booked) {
        isBooked = booked;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }
}
