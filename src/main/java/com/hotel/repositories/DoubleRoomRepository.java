package com.hotel.repositories;

import com.hotel.models.rooms.DoubleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoubleRoomRepository extends JpaRepository<DoubleRoom, Long> {
    @Query(value = "SELECT * FROM double_room WHERE number = :room AND hotel_id = :hotel", nativeQuery = true)
    Optional<DoubleRoom> findRoomByRoomNumber(@Param("room") String roomNumber, @Param("hotel") Long hotelId);

    @Query(value = "SELECT * FROM double_room WHERE is_booked = false AND hotel_id = :hotel", nativeQuery = true)
    List<DoubleRoom> findAvailableDoubleRooms(@Param("hotel") Long hotelId);
}
