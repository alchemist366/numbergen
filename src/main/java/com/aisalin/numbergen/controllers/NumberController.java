package com.aisalin.numbergen.controllers;

import com.aisalin.numbergen.converters.CarNumberConverter;
import com.aisalin.numbergen.services.CarNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {

    private final CarNumberService carNumberService;
    private final CarNumberConverter carNumberConverter;

    @Autowired
    public NumberController(CarNumberService carNumberService, CarNumberConverter carNumberConverter) {
        this.carNumberService = carNumberService;
        this.carNumberConverter = carNumberConverter;
    }

    @GetMapping("/number/random")
    public String random() {
        return carNumberConverter.convert(carNumberService.random());
    }

    @GetMapping("/number/next")
    public String hello() {
        return carNumberConverter.convert(carNumberService.next());
    }
}
