package com.hotel.models.rooms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.models.Hotel;
import com.hotel.models.auth.User;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private Integer numberOfBeds;
    private Boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"single_rooms", "double_rooms", "suites"})
    private Hotel hotel;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"single_room", "double_room", "suite", "roles"})
    private User guest;

    public Room() {
    }

    public Room(String number, Integer numberOfBeds, Hotel hotel) {
        this.number = number;
        this.numberOfBeds = numberOfBeds;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
