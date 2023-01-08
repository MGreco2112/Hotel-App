package com.hotel.controllers;

import com.hotel.Services.UserService;
import com.hotel.models.Hotel;
import com.hotel.models.auth.User;
import com.hotel.models.rooms.*;
import com.hotel.payloads.request.*;
import com.hotel.payloads.response.UserResponse;
import com.hotel.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<Hotel> findAllHotels() {
        return repository.findAll();
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> findLoggedInUser() {
        User selUser = userService.getCurrentUser();

        if (selUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new UserResponse(
                selUser.getUsername(),
                selUser.getSingleRoom() != null ? selUser.getSingleRoom().getNumber() : null,
                selUser.getDoubleRoom() != null ? selUser.getDoubleRoom().getNumber() : null,
                selUser.getSuite() != null ? selUser.getSuite().getNumber() : null
        ));
    }

    @GetMapping("/rooms")
    @PreAuthorize("hasRole('ADMIN')")
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
//
//    @GetMapping("/room/single/{number}")
//    public ResponseEntity<SingleRoom> findSingleRoomByNumber(@PathVariable String number) {
//        SingleRoom selRoom = singleRoomRepository.findByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        return ResponseEntity.ok(selRoom);
//    }
//
//    @GetMapping("/room/double/{number}")
//    public ResponseEntity<DoubleRoom> findDoubleRoomByNumber(@PathVariable String number) {
//        DoubleRoom selRoom = doubleRoomRepository.findRoomByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        return ResponseEntity.ok(selRoom);
//    }
//
//    @GetMapping("/room/suite/{number}")
//    public ResponseEntity<Suite> findSuiteByNumber(@PathVariable String number) {
//        Suite selRoom = suiteRepository.findRoomByRoomNumber(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        return ResponseEntity.ok(selRoom);
//    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Hotel> createHotel(@RequestBody NewHotelRequest newHotelReq) {
        Hotel newHotel = new Hotel(
                newHotelReq.getName()
        );

        return new ResponseEntity<>(repository.save(newHotel), HttpStatus.CREATED);
    }

    @PostMapping("/singleRoom")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SingleRoom> createSingleRoom(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SingleRoom newRoom = new SingleRoom(
                request.getRoomNumber(),
                1,
                selHotel
        );

        SingleRoom savedRoom = singleRoomRepository.save(newRoom);

        selHotel.addSingleRoom(newRoom);

        repository.save(selHotel);

        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PostMapping("/doubleRoom")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoubleRoom> createDoubleRoom(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        DoubleRoom newRoom = new DoubleRoom(
                request.getRoomNumber(),
                2,
                selHotel
        );

        DoubleRoom savedRoom = doubleRoomRepository.save(newRoom);

        selHotel.addDoubleRoom(newRoom);

        repository.save(selHotel);

        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PostMapping("/suite")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Suite> createSuite(@RequestBody NewRoomRequest request) {
        Hotel selHotel = repository.findById(request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Suite newRoom = new Suite(
                request.getRoomNumber(),
                3,
                selHotel
        );

        Suite savedRoom = suiteRepository.save(newRoom);

        selHotel.addSuite(newRoom);

        repository.save(selHotel);

        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @PutMapping("/room/bookRoom")
    public ResponseEntity<User> bookFirstAvailableRoom(@RequestBody BookRoomRequest request) {
        User selUser = userService.getCurrentUser();

        if (selUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (request.getRoomNumber() != null) {
            switch (request.getRoomNumber().substring(0, 1)) {
                case "1" -> {
                    SingleRoom selRoom = singleRoomRepository.findByRoomNumber(request.getRoomNumber(), request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                    selRoom.setGuest(selUser);

                    selRoom.setIsBooked(true);

                    SingleRoom savedRoom = singleRoomRepository.save(selRoom);

                    selUser.setSingleRoom(savedRoom);
                }
                case "2" -> {
                    DoubleRoom selRoom = doubleRoomRepository.findRoomByRoomNumber(request.getRoomNumber(), request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                    selRoom.setGuest(selUser);

                    selRoom.setIsBooked(true);

                    DoubleRoom savedRoom = doubleRoomRepository.save(selRoom);

                    selUser.setDoubleRoom(savedRoom);
                }
                case "3" -> {
                    Suite selRoom = suiteRepository.findRoomByRoomNumber(request.getRoomNumber(), request.getHotelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                    selRoom.setGuest(selUser);

                    selRoom.setIsBooked(true);

                    Suite savedRoom = suiteRepository.save(selRoom);

                    selUser.setSuite(savedRoom);
                }
                default -> {return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);}
            }
        } else {
            switch (request.getRoomType()) {
                case "single" -> {
                    List<SingleRoom> roomList = singleRoomRepository.findAvailableSingleRooms(request.getHotelId());

                    if (roomList.size() > 0) {
                        SingleRoom selRoom = roomList.get(0);

                        selRoom.setGuest(selUser);

                        selRoom.setIsBooked(true);

                        SingleRoom savedRoom = singleRoomRepository.save(selRoom);

                        selUser.setSingleRoom(savedRoom);
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    }
                }
                case "double" -> {
                    List<DoubleRoom> roomList = doubleRoomRepository.findAvailableDoubleRooms(request.getHotelId());

                    if (roomList.size() > 0) {
                        DoubleRoom selRoom = roomList.get(0);

                        selRoom.setGuest(selUser);

                        selRoom.setIsBooked(true);

                        DoubleRoom savedRoom = doubleRoomRepository.save(selRoom);

                        selUser.setDoubleRoom(savedRoom);
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    }
                }
                case "suite" -> {
                    List<Suite> roomList = suiteRepository.findAvailableSuites(request.getHotelId());

                    if (roomList.size() > 0) {
                        Suite selRoom = roomList.get(0);

                        selRoom.setGuest(selUser);

                        selRoom.setIsBooked(true);

                        Suite savedRoom = suiteRepository.save(selRoom);

                        selUser.setSuite(savedRoom);
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    }
                }
                case "default" -> {return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);}
            }

        }

//        selUser = userRepository.save(selUser);

//        return ResponseEntity.ok(new UserResponse(
//                selUser.getUsername(),
//                selUser.getSingleRoom() != null ? selUser.getSingleRoom().getNumber() : null,
//                selUser.getDoubleRoom() != null ? selUser.getDoubleRoom().getNumber() : null,
//                selUser.getSuite() != null ? selUser.getSuite().getNumber() : null
//        ));

        return ResponseEntity.ok(userRepository.save(selUser));
    }

    @PutMapping("/room/openSingleRoom")
    public ResponseEntity<SingleRoom> openSingleRoom(@RequestBody BookRoomRequest request) {
        SingleRoom selRoom = singleRoomRepository.findById(Long.parseLong(request.getRoomNumber())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setGuest(null);
        selRoom.setIsBooked(false);

        return ResponseEntity.ok(singleRoomRepository.save(selRoom));
    }

    @PutMapping("/room/openDoubleRoom")
    public ResponseEntity<DoubleRoom> openDoubleRoom(@RequestBody BookRoomRequest request) {
        DoubleRoom selRoom = doubleRoomRepository.findById(Long.parseLong(request.getRoomNumber())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setGuest(null);
        selRoom.setIsBooked(false);

        return ResponseEntity.ok(doubleRoomRepository.save(selRoom));
    }

    @PutMapping("/room/openSuite")
    public ResponseEntity<Suite> openSuite(@RequestBody BookRoomRequest request) {
        Suite selRoom = suiteRepository.findById(Long.parseLong(request.getRoomNumber())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setGuest(null);
        selRoom.setIsBooked(false);

        return ResponseEntity.ok(suiteRepository.save(selRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSingleRoomById(@PathVariable Long id) {
        SingleRoom selRoom = singleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        singleRoomRepository.save(selRoom);

        singleRoomRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }

    @DeleteMapping("/room/double/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDoubleRoomById(@PathVariable Long id) {
        DoubleRoom selRoom = doubleRoomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        doubleRoomRepository.save(selRoom);

        doubleRoomRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }

    @DeleteMapping("/room/suite/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSuiteById(@PathVariable Long id) {
        Suite selRoom = suiteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        selRoom.setHotel(null);
        selRoom.setGuest(null);

        suiteRepository.save(selRoom);

        suiteRepository.delete(selRoom);

        return ResponseEntity.ok("Deleted Room");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        User selUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userRepository.delete(selUser);

        return ResponseEntity.ok("Deleted User");
    }
}
