package com.hotel.repositories;

import com.hotel.models.rooms.DoubleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoubleRoomRepository extends JpaRepository<DoubleRoom, Long> {
}
