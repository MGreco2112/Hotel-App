package com.hotel.payloads.request;

public class NewHotelRequest {
    private String name;

    public NewHotelRequest(){};

    public NewHotelRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
