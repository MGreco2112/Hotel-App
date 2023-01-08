package com.hotel.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hotel.models.rooms.DoubleRoom;
import com.hotel.models.rooms.SingleRoom;
import com.hotel.models.rooms.Suite;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    Set<Role> roles;

    public User() {

    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "single_room_id", referencedColumnName = "id")
    @JsonIncludeProperties("number")
    private SingleRoom singleRoom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "double_room_id", referencedColumnName = "id")
    @JsonIncludeProperties("number")
    private DoubleRoom doubleRoom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suite_id", referencedColumnName = "id")
    @JsonIncludeProperties("number")
    private Suite suite;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
