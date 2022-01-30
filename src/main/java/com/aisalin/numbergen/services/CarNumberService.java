package com.aisalin.numbergen.services;

import com.aisalin.numbergen.components.NumberStorage;
import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.utils.RandomUtils;
import com.aisalin.numbergen.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarNumberService {

    private final NumberStorage numberStorage;

    private final String availableLetters;

    @Autowired
    public CarNumberService(NumberStorage numberStorage) {
        this.numberStorage = numberStorage;
        this.availableLetters = StringUtils.sortDistinctChars("АЕТОРНУКХСВМ");
    }

    public CarNumber next() {
        CarNumber currentCarNumber = numberStorage.getCarNumber();
        return next(currentCarNumber);
    }

    public CarNumber next(CarNumber carNumber) {
        int nextNumberPart = (carNumber.getNumberPart() + 1) % 1000;
        CarNumber nextNumber = CarNumber.builder()
                .numberPart(nextNumberPart)
                .letterPart(nextNumberPart == 0
                        ? StringUtils.nextWordModal(carNumber.getLetterPart(), availableLetters)
                        : carNumber.getLetterPart())
                .build();
        numberStorage.setCarNumber(nextNumber);
        return nextNumber;
    }

    public CarNumber random() {
        CarNumber randNumber = CarNumber.builder()
                .numberPart(RandomUtils.randomInt(0, 999))
                .letterPart(RandomUtils.randomWord(3, availableLetters))
                .build();
        numberStorage.setCarNumber(randNumber);
        return randNumber;
    }

}
