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
    @Query(value = "SELECT * FROM suite WHERE room_number = :query", nativeQuery = true)
    Optional<Suite> findRoomByRoomNumber(@Param("query") String roomNumber);

    @Query(value = "SELECT * FROM suite WHERE is_booked = false", nativeQuery = true)
    List<Suite> findAvailableSuites();
}
