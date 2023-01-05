package com.hotel.payloads.request;

public class UpdateHotelRequest {
    private String name;

    public UpdateHotelRequest() {
    }

    public UpdateHotelRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
