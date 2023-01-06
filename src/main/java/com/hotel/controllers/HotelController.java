package com.hotel.controllers;

import com.hotel.Services.UserService;
import com.hotel.models.Hotel;
import com.hotel.models.rooms.*;
import com.hotel.payloads.request.NewHotelRequest;
import com.hotel.payloads.request.NewRoomRequest;
import com.hotel.payloads.request.UpdateHotelRequest;
import com.hotel.payloads.request.UpdateRoomRequest;
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
    @Autowired
    private UserService userService;

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

    @GetMapping("/room/single/{number}")
    public ResponseEntity<SingleRoom> findSingleRoomByNumber(@PathVariable String number) {
        SingleRoom selRoom = singleRoomRepository.findByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selRoom);
    }

    @GetMapping("/room/double/{number}")
    public ResponseEntity<DoubleRoom> findDoubleRoomByNumber(@PathVariable String number) {
        DoubleRoom selRoom = doubleRoomRepository.findRoomByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selRoom);
    }

    @GetMapping("/room/suite/{number}")
    public ResponseEntity<Suite> findSuiteByNumber(@PathVariable String number) {
        Suite selRoom = suiteRepository.findRoomByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(selRoom);
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

        return new ResponseEntity<>(repository.save(selHotel), HttpStatus.ACCEPTED);
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoomById(@PathVariable Long id, @RequestBody UpdateRoomRequest updates) {
        switch (updates.getRoomType()) {
            case "single" -> {
                SingleRoom selRoom = singleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                if (updates.getNumber() != null) {
                    selRoom.setNumber(updates.getNumber());
                }
                if (updates.getNumberOfBeds() != null) {
                    selRoom.setNumberOfBeds(updates.getNumberOfBeds());
                }
                if (updates.getHotel() != null) {
                    selRoom.setHotel(updates.getHotel());
                }
                if (updates.getGuest() != null) {
                    selRoom.setGuest(updates.getGuest());
                }

                return new ResponseEntity<>(singleRoomRepository.save(selRoom), HttpStatus.ACCEPTED);
            }
            case "double" -> {
                DoubleRoom selRoom = doubleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                if (updates.getNumber() != null) {
                    selRoom.setNumber(updates.getNumber());
                }
                if (updates.getNumberOfBeds() != null) {
                    selRoom.setNumberOfBeds(updates.getNumberOfBeds());
                }
                if (updates.getHotel() != null) {
                    selRoom.setHotel(updates.getHotel());
                }
                if (updates.getGuest() != null) {
                    selRoom.setGuest(updates.getGuest());
                }

                return new ResponseEntity<>(doubleRoomRepository.save(selRoom), HttpStatus.ACCEPTED);
            }
            case "suite" -> {
                Suite selRoom = suiteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                if (updates.getNumber() != null) {
                    selRoom.setNumber(updates.getNumber());
                }
                if (updates.getNumberOfBeds() != null) {
                    selRoom.setNumberOfBeds(updates.getNumberOfBeds());
                }
                if (updates.getHotel() != null) {
                    selRoom.setHotel(updates.getHotel());
                }
                if (updates.getGuest() != null) {
                    selRoom.setGuest(updates.getGuest());
                }

                return new ResponseEntity<>(suiteRepository.save(selRoom), HttpStatus.ACCEPTED);
            }
            default -> {
                return new ResponseEntity<>("INVALID UPDATE OBJECT", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotelById(@PathVariable Long id) {
        Hotel selHotel = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selHotel.setSingleRooms(null);
        selHotel.setDoubleRooms(null);
        selHotel.setSuites(null);

        repository.save(selHotel);

        repository.delete(selHotel);

        return ResponseEntity.ok("Deleted Hotel");
    }

    @DeleteMapping("/room/single/{id}")
    public ResponseEntity<String> deleteSingleRoomById(@PathVariable Long id) {
        SingleRoom selRoom = singleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        singleRoomRepository.save(selRoom);

        singleRoomRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }

    @DeleteMapping("/room/double/{id}")
    public ResponseEntity<String> deleteDoubleRoomById(@PathVariable Long id) {
        DoubleRoom selRoom = doubleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        doubleRoomRepository.save(selRoom);

        doubleRoomRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }

    @DeleteMapping("/room/suite/{id}")
    public ResponseEntity<String> deleteSuiteById(@PathVariable Long id) {
        Suite selRoom = suiteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        suiteRepository.save(selRoom);

        suiteRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }
}
