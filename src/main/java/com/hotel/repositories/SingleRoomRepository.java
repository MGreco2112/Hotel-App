package com.hotel.repositories;

import com.hotel.models.rooms.SingleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleRoomRepository extends JpaRepository<SingleRoom, Long> {
}
