package com.hotel.models.rooms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.models.Hotel;
import com.hotel.models.auth.User;
import jakarta.persistence.*;

@Entity
public class SingleRoom implements RoomInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private Integer numberOfBeds;
    private Boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @JsonIgnoreProperties("singleRooms")
    private Hotel hotel;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"singleRoom", "doubleRoom", "suite", "roles"})
//    @JsonIgnore
    private User guest;

    public SingleRoom(){}

    public SingleRoom(String number, Integer numberOfBeds, Hotel hotel) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
        this.hotel = hotel;
    }

    @Override
    public void bookRoom(User guest) {
        this.guest = guest;
        isBooked = true;
    }

    @Override
    public void openRoom() {
        this.guest = null;
        isBooked = false;
    }

    public Long getId() {
        return id;
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public void setGuest(User guest) {
        this.guest = guest;
    }

    @Override
    public User getGuest() {
        return guest;
    }

    @Override
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean booked) {
        isBooked = booked;
    }
}
