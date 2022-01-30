package com.aisalin.numbergen.repositories;

import com.aisalin.numbergen.models.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarNumberRepository extends JpaRepository<CarNumber, Long> {
    CarNumber findTopByOrderByIdDesc();
}
