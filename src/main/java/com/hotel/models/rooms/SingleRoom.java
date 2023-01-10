package com.hotel.models.rooms;

import com.hotel.models.Hotel;
import jakarta.persistence.*;

@Entity
public class SingleRoom extends Room {

    public SingleRoom(){}

    public SingleRoom(String number, Integer numberOfBeds, Hotel hotel) {
        super(number, numberOfBeds, hotel);
    }

}
