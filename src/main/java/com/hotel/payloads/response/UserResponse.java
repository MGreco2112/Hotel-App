package com.hotel.payloads.response;

import com.hotel.models.auth.Role;
import com.hotel.models.rooms.DoubleRoom;
import com.hotel.models.rooms.SingleRoom;
import com.hotel.models.rooms.Suite;

import java.util.Set;

public class UserResponse {
    private String username;

    private SingleRoom singleRoom;
    private DoubleRoom doubleRoom;
    private Suite suite;

    public UserResponse() {
    }

    public UserResponse(String username, String singleRoom, String doubleRoom, String suite) {
        this.username = username;
        if (singleRoom != null) {
            this.singleRoom = new SingleRoom();
            this.singleRoom.setNumber(singleRoom);
        }
        if (doubleRoom != null) {
            this.doubleRoom = new DoubleRoom();
            this.doubleRoom.setNumber(doubleRoom);
        }
        if (suite != null) {
            this.suite = new Suite();
            this.suite.setNumber(suite);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SingleRoom getSingleRoom() {
        return singleRoom;
    }

    public void setSingleRoom(SingleRoom singleRoom) {
        this.singleRoom = singleRoom;
    }

    public DoubleRoom getDoubleRoom() {
        return doubleRoom;
    }

    public void setDoubleRoom(DoubleRoom doubleRoom) {
        this.doubleRoom = doubleRoom;
    }

    public Suite getSuite() {
        return suite;
    }

    public void setSuite(Suite suite) {
        this.suite = suite;
    }
}
