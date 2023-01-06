package com.hotel.repositories;

import com.hotel.models.rooms.SingleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleRoomRepository extends JpaRepository<SingleRoom, Long> {
    @Query(value = "SELECT * FROM single_room WHERE room_number = :query", nativeQuery = true)
    Optional<SingleRoom> findByRoomNumber(@Param("query") String roomNumber);

    @Query(value = "SELECT * FROM single_room WHERE is_booked = false", nativeQuery = true)
    List<SingleRoom> findAvailableSingleRooms();
}
