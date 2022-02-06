package com.aisalin.numbergen.repositories;

import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarNumberRepository extends JpaRepository<CarNumber, Long> {
    CarNumber findTopByOrderByIdDesc();
    List<CarNumber> findByLetterPartAndRegionOrderByLetterPartAsc(String letterPart, Region region);
    CarNumber findFirstByRegionOrderByCreatedDesc(Region region);
    List<CarNumber> findAllByRegion(Region region);
}
