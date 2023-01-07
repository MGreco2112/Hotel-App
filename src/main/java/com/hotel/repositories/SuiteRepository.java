package com.hotel.repositories;

import com.hotel.models.rooms.Suite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuiteRepository extends JpaRepository<Suite, Long> {
    @Query(value = "SELECT * FROM suite WHERE number = :room AND hotel_id = :hotel", nativeQuery = true)
    Optional<Suite> findRoomByRoomNumber(@Param("room") String roomNumber, @Param("hotel") Long hotelId);

    @Query(value = "SELECT * FROM suite WHERE is_booked = false AND hotel_id = :hotel", nativeQuery = true)
    List<Suite> findAvailableSuites(@Param("hotel") Long hotelId);
}
