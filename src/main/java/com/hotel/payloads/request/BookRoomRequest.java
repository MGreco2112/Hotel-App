package com.hotel.payloads.request;

import com.hotel.models.auth.User;

public class BookRoomRequest {
    private String roomType;
    private String roomNumber;
    private Long hotelId;

    public BookRoomRequest() {
    }

    public BookRoomRequest(String roomType, String roomNumber, Long hotelId) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}
