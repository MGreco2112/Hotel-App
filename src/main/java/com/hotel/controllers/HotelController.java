package com.hotel.controllers;

import com.hotel.models.Hotel;
import com.hotel.payloads.request.NewHotelRequest;
import com.hotel.payloads.request.UpdateHotelRequest;
import com.hotel.repositories.HotelRepository;
import com.hotel.repositories.UserRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelRepository repository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Hotel> findAllHotels() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable Long id) {
        Hotel selHotel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selHotel);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody NewHotelRequest newHotelReq) {
        Hotel newHotel = new Hotel(
                newHotelReq.getName()
        );

        return new ResponseEntity<>(repository.save(newHotel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotelById(@PathVariable Long id, @RequestBody UpdateHotelRequest updates) {
        Hotel selHotel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updates.getName() != null) {
            selHotel.setName(updates.getName());
        }

        return ResponseEntity.ok(repository.save(selHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotelById(@PathVariable Long id) {
        Hotel selHotel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(selHotel);

        return ResponseEntity.ok("Deleted Hotel");
    }
}
