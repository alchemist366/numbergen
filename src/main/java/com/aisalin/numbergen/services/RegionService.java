package com.aisalin.numbergen.services;

import com.aisalin.numbergen.models.Region;
import com.aisalin.numbergen.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    public Region findByCode(Integer code) {
        return repository.findFirstByCode(code);
    }
}
