package com.hotel.models.rooms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.models.Hotel;
import jakarta.persistence.*;

@Entity
public class Suite extends Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rooms")
    private Hotel hotel;

    public Suite(){};

    public Suite(String number, Integer numberOfBeds, Hotel hotel) {
        super(number, numberOfBeds);
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
