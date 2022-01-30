package com.aisalin.numbergen.repositories;

import com.aisalin.numbergen.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findFirstByCode(Integer code);
}
