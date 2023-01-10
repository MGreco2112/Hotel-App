package com.hotel.models.rooms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.models.Hotel;
import com.hotel.models.auth.User;
import jakarta.persistence.*;

@Entity
public class DoubleRoom extends Room {

    public DoubleRoom(){};

    public DoubleRoom(String number, Integer numberOfBeds, Hotel hotel) {
        super(number, numberOfBeds, hotel);
    }

}
