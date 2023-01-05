package com.hotel.controllers;

import com.hotel.models.Hotel;
import com.hotel.models.rooms.*;
import com.hotel.payloads.request.NewHotelRequest;
import com.hotel.payloads.request.NewRoomRequest;
import com.hotel.payloads.request.UpdateHotelRequest;
import com.hotel.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SingleRoomRepository singleRoomRepository;
    @Autowired
    private DoubleRoomRepository doubleRoomRepository;
    @Autowired
    private SuiteRepository suiteRepository;

    @GetMapping
    public List<Hotel> findAllHotels() {
        return repository.findAll();
    }

    @GetMapping("/rooms")
    public List<RoomInterface> findAllRooms() {
        List<RoomInterface> rooms = new ArrayList<>();

        rooms.addAll(singleRoomRepository.findAll());
        rooms.addAll(doubleRoomRepository.findAll());
        rooms.addAll(suiteRepository.findAll());

        return rooms;
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

    @PostMapping("/singleRoom")
    public ResponseEntity<SingleRoom> createSingleRoom(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SingleRoom newRoom = new SingleRoom(
                request.getRoomNumber(),
                1,
                selHotel
        );

        repository.save(selHotel);

        return new ResponseEntity<>(singleRoomRepository.save(newRoom), HttpStatus.CREATED);
    }

    @PostMapping("/doubleRoom")
    public ResponseEntity<DoubleRoom> createDoubleRoom(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        DoubleRoom newRoom = new DoubleRoom(
                request.getRoomNumber(),
                2,
                selHotel
        );

        return new ResponseEntity<>(doubleRoomRepository.save(newRoom), HttpStatus.CREATED);
    }

    @PostMapping("/suite")
    public ResponseEntity<Suite> createSuite(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Suite newRoom = new Suite(
                request.getRoomNumber(),
                3,
                selHotel
        );

        return new ResponseEntity<>(suiteRepository.save(newRoom), HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotelById(@PathVariable Long id, @RequestBody UpdateHotelRequest updates) {
        Hotel selHotel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updates.getName() != null) {
            selHotel.setName(updates.getName());
        }
        if (updates.getSingleRooms() != null) {
            selHotel.setSingleRooms(updates.getSingleRooms());
        }
        if (updates.getDoubleRooms() != null) {
            selHotel.setDoubleRooms(updates.getDoubleRooms());
        }
        if (updates.getSuites() != null) {
            selHotel.setSuites(updates.getSuites());
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
