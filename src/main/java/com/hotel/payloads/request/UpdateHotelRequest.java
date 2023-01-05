package com.hotel.payloads.request;

import com.hotel.models.rooms.DoubleRoom;
import com.hotel.models.rooms.SingleRoom;
import com.hotel.models.rooms.Suite;

import java.util.List;
import java.util.Set;

public class UpdateHotelRequest {
    private String name;
    private Set<SingleRoom> singleRooms;
    private Set<DoubleRoom> doubleRooms;
    private Set<Suite> suites;

    public UpdateHotelRequest() {
    }

    public UpdateHotelRequest(String name, Set<SingleRoom> singleRooms, Set<DoubleRoom> doubleRooms, Set<Suite> suites) {
        this.name = name;
        this.singleRooms = singleRooms;
        this.doubleRooms = doubleRooms;
        this.suites = suites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SingleRoom> getSingleRooms() {
        return singleRooms;
    }

    public void setSingleRooms(Set<SingleRoom> singleRooms) {
        this.singleRooms = singleRooms;
    }

    public Set<DoubleRoom> getDoubleRooms() {
        return doubleRooms;
    }

    public void setDoubleRooms(Set<DoubleRoom> doubleRooms) {
        this.doubleRooms = doubleRooms;
    }

    public Set<Suite> getSuites() {
        return suites;
    }

    public void setSuites(Set<Suite> suites) {
        this.suites = suites;
    }
}
