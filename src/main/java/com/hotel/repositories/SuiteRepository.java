package com.hotel.repositories;

import com.hotel.models.rooms.Suite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuiteRepository extends JpaRepository<Suite, Long> {
}
