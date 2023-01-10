package com.hotel.models.rooms;

import com.hotel.models.Hotel;
import jakarta.persistence.*;

@Entity
public class Suite extends Room {
    public Suite(){};

    public Suite(String number, Integer numberOfBeds, Hotel hotel) {
        super(number, numberOfBeds, hotel);
    }

}
