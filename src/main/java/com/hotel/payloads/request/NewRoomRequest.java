package com.hotel.payloads.request;

public class NewRoomRequest {
    private Long hotelId;
    private String roomNumber;
    private String roomType;

    public NewRoomRequest(){};

    public NewRoomRequest(Long hotelId, String roomNumber, String roomType) {
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
