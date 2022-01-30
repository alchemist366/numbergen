package com.aisalin.numbergen.services;

import com.aisalin.numbergen.components.NumberStorage;
import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.repositories.CarNumberRepository;
import com.aisalin.numbergen.utils.RandomUtils;
import com.aisalin.numbergen.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CarNumberService {

    private final CarNumberRepository repository;
    private final NumberStorage numberStorage;
    private final String availableLetters;
    private final RegionService regionService;

    private static final int TAT_REGION_CODE = 116;

    @Autowired
    public CarNumberService(CarNumberRepository carNumberRepository, NumberStorage numberStorage, RegionService regionService) {
        this.repository = carNumberRepository;
        this.numberStorage = numberStorage;
        this.availableLetters = StringUtils.sortDistinctChars("АЕТОРНУКХСВМ");
        this.regionService = regionService;
    }

    public void saveCurrent() {
        save(getCurrentNumber());
    }

    public void save(CarNumber carNumber) {
        log.info("save car number {}", carNumber.toString());
        repository.save(carNumber);
    }

    public CarNumber getCurrentNumber() {
        CarNumber carNumber = numberStorage.getCarNumber();
        if (Objects.isNull(carNumber)) {
            carNumber = repository.findTopByOrderByIdDesc();
        }
        if (Objects.isNull(carNumber)) {
            carNumber = create();
        }
        return carNumber;
    }

    public CarNumber next() {
        CarNumber currentCarNumber = getCurrentNumber();
        return next(currentCarNumber);
    }

    public CarNumber next(CarNumber carNumber) {
        int nextNumberPart = (carNumber.getNumberPart() + 1) % 1000;
        CarNumber nextNumber = create(nextNumberPart, nextNumberPart == 0
                ? StringUtils.nextWordModal(carNumber.getLetterPart(), availableLetters)
                : carNumber.getLetterPart());
        numberStorage.setCarNumber(nextNumber);
        return nextNumber;
    }

    public CarNumber random() {
        CarNumber randNumber = create(RandomUtils.randomInt(0, 999), RandomUtils.randomWord(3, availableLetters));
        numberStorage.setCarNumber(randNumber);
        return randNumber;
    }

    public CarNumber create() {
        return create(0, "ААА");
    }

    public CarNumber create(int numberPart, String letterPart) {
        return CarNumber.builder()
                .letterPart(letterPart)
                .numberPart(numberPart)
                .region(regionService.findByCode(TAT_REGION_CODE))
                .build();
    }
}
