package com.aisalin.numbergen.controllers;

import com.aisalin.numbergen.converters.CarNumberConverter;
import com.aisalin.numbergen.exceptions.NumbersIsOverException;
import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.services.CarNumberService;
import com.aisalin.numbergen.services.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class NumberController {

    private static final int TAT_REGION_CODE = 116;

    private final CarNumberService carNumberService;
    private final CarNumberConverter carNumberConverter;
    private final RegionService regionService;

    @Autowired
    public NumberController(CarNumberService carNumberService, CarNumberConverter carNumberConverter, RegionService regionService) {
        this.carNumberService = carNumberService;
        this.carNumberConverter = carNumberConverter;
        this.regionService = regionService;
    }

    @GetMapping("/number/random")
    public String random() throws NumbersIsOverException {
        log.info("/number/random request");
        CarNumber randomCarNumber = carNumberService.random(regionService.findByCode(TAT_REGION_CODE));
        carNumberService.save(randomCarNumber);
        return carNumberConverter.convert(randomCarNumber);
    }

    @GetMapping("/number/next")
    public String next() throws NumbersIsOverException {
        log.info("/number/next request");
        CarNumber nextCarNumber = carNumberService.next(regionService.findByCode(TAT_REGION_CODE));
        carNumberService.save(nextCarNumber);
        return carNumberConverter.convert(nextCarNumber);
    }
}
