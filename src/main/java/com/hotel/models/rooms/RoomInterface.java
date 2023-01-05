package com.hotel.models.rooms;

import com.hotel.models.Hotel;
import com.hotel.models.auth.User;

public interface RoomInterface {
    Long id = null;
    String number = null;
    Integer numberOfBeds = null;
    Boolean isBooked = false;
    Hotel hotel = null;
    User guest = null;



    void bookRoom(User guest);
    void openRoom();

    Long getId();
    void setNumber(String number);
    String getNumber();
    void setNumberOfBeds(Integer numberOfBeds);
    Integer getNumberOfBeds();
    void setHotel(Hotel hotel);
    Hotel getHotel();
    void setGuest(User guest);
    User getGuest();

}
