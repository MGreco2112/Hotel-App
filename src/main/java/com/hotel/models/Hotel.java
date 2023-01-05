package com.hotel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.models.rooms.DoubleRoom;
import com.hotel.models.rooms.SingleRoom;
import com.hotel.models.rooms.Suite;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //todo add rooms connection
    @OneToMany
    @JoinColumn(name = "single_room_id", referencedColumnName = "id")
    @JsonIgnoreProperties("hotel")
    private Set<SingleRoom> singleRooms;

    @OneToMany
    @JoinColumn(name = "double_room_id", referencedColumnName = "id")
    @JsonIgnoreProperties("hotel")
    private Set<DoubleRoom> doubleRooms;

    @OneToMany
    @JoinColumn(name= "suite_id", referencedColumnName = "id")
    @JsonIgnoreProperties("hotel")
    private Set<Suite> suites;

    public Hotel() {

    }

    public Hotel(String name) {

    }

    public Long getId() {
        return id;
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

    public void addSingleRoom(SingleRoom room) {
        singleRooms.add(room);
    }

    public void removeSingleRoom(SingleRoom room) {
        singleRooms.remove(room);
    }

    public Set<DoubleRoom> getDoubleRooms() {
        return doubleRooms;
    }

    public void setDoubleRooms(Set<DoubleRoom> doubleRooms) {
        this.doubleRooms = doubleRooms;
    }

    public void addDoubleRoom(DoubleRoom room) {
        doubleRooms.add(room);
    }

    public void removeDoubleRoom(DoubleRoom room) {
        doubleRooms.remove(room);
    }

    public Set<Suite> getSuites() {
        return suites;
    }

    public void setSuites(Set<Suite> suites) {
        this.suites = suites;
    }

    public void addSuite(Suite room) {
        suites.add(room);
    }

    public void removeSuite(Suite room) {
        suites.remove(room);
    }
}
