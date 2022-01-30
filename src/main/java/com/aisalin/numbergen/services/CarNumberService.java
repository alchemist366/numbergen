package com.aisalin.numbergen.services;

import com.aisalin.numbergen.components.NumberStorage;
import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.repositories.CarNumberRepository;
import com.aisalin.numbergen.utils.RandomUtils;
import com.aisalin.numbergen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
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

    /**
     * save car number from storage
     */
    public void saveCurrent() {
        save(getCurrentNumber());
    }

    public void save(CarNumber carNumber) {
        repository.save(carNumber);
    }

    /**
     * @return car number from storage or last from database or default
     */
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

    /**
     * @return next car number in system
     */
    public CarNumber next() {
        CarNumber currentCarNumber = getCurrentNumber();
        return next(currentCarNumber);
    }

    /**
     * @param carNumber
     * @return next cur number after specified in param
     */
    public CarNumber next(CarNumber carNumber) {
        int nextNumberPart = (carNumber.getNumberPart() + 1) % 1000;
        CarNumber nextNumber = create(nextNumberPart, nextNumberPart == 0
                ? StringUtils.nextWordModal(carNumber.getLetterPart(), availableLetters)
                : carNumber.getLetterPart());
        numberStorage.setCarNumber(nextNumber);
        return nextNumber;
    }

    /**
     * generate car number with 3 digits integer main part, 3 chars letter part and Tat region
     * @return generated car number
     */
    public CarNumber random() {
        CarNumber randNumber = create(RandomUtils.randomIntInclude(0, 999), RandomUtils.randomWord(3, availableLetters));
        numberStorage.setCarNumber(randNumber);
        return randNumber;
    }

    /**
     * create default car number А000АА 116 RUS
     * @return created car number
     */
    public CarNumber create() {
        return create(0, "ААА");
    }

    /**
     * create car number by params. A111AA XXX RUS. X - region code
     * @param numberPart as 1 in description
     * @param letterPart as A in description
     * @return created car number
     */
    public CarNumber create(int numberPart, String letterPart) {
        return CarNumber.builder()
                .letterPart(letterPart)
                .numberPart(numberPart)
                .region(regionService.findByCode(TAT_REGION_CODE))
                .build();
    }
}
