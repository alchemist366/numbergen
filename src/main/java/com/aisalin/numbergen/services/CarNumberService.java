package com.aisalin.numbergen.services;

import com.aisalin.numbergen.exceptions.NumbersIsOverException;
import com.aisalin.numbergen.models.CarNumber;
import com.aisalin.numbergen.models.Region;
import com.aisalin.numbergen.repositories.CarNumberRepository;
import com.aisalin.numbergen.utils.RandomUtils;
import com.aisalin.numbergen.utils.ProjStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Slf4j
public class CarNumberService {

    private final CarNumberRepository repository;
    private final String availableLetters;

    @Autowired
    public CarNumberService(CarNumberRepository carNumberRepository) {
        this.repository = carNumberRepository;
        this.availableLetters = ProjStringUtils.sortDistinctChars("АЕТОРНУКХСВМ").orElse("");
    }

    public void save(CarNumber carNumber) {
        repository.save(carNumber);
    }

    public Optional<CarNumber> findLastCreatedCarNumber(Region region) {
        return Optional.ofNullable(repository.findFirstByRegionOrderByCreatedDesc(region));
    }

    /**
     * @return next car number in system
     */
    public CarNumber next(Region region) throws NumbersIsOverException {
        Optional<CarNumber> lastCreatedCarNumber = findLastCreatedCarNumber(region);
        if (lastCreatedCarNumber.isPresent()) {
            return next(lastCreatedCarNumber.get());
        } else {
            return create(region);
        }
    }

    /**
     *
     * @param carNumber
     * @return next cur number after specified in param that wasn't before
     * @throws NumbersIsOverException when all numbers in region where used
     */
    public CarNumber next(CarNumber carNumber) throws NumbersIsOverException {
        if (isAllNumbersInRegionWhereUsed(carNumber.getRegion())) {
            throw new NumbersIsOverException("not exists next number that wasn't before");
        }
        int numberPart = carNumber.getNumberPart();
        String letterPart = carNumber.getLetterPart();
        List<CarNumber> byLetterPart = findByLetterPart(carNumber.getRegion(), carNumber.getLetterPart());
        do {
            numberPart = (numberPart + 1) % 1000;
            if (numberPart == 0) {
                letterPart = ProjStringUtils.nextWordModal(carNumber.getLetterPart(), availableLetters);
                byLetterPart = findByLetterPart(carNumber.getRegion(), carNumber.getLetterPart());
            }
        } while (!isFreeNumberPart(byLetterPart, numberPart));
        return create(numberPart, letterPart, carNumber.getRegion());
    }

    public boolean isAllNumbersInRegionWhereUsed(Region region) {
        List<CarNumber> carNumbers = repository.findAllByRegion(region);
        double maxNumberCount = Math.pow(availableLetters.length(), 3) * 1000;
        return carNumbers.size() == maxNumberCount;
    }

    /**
     * generate car number with 3 digits integer main part, 3 chars letter part and Tat region
     * generated number is unique by that 3 params
     * @return generated car number
     */
    public CarNumber random(Region region) throws NumbersIsOverException {
        String letterPart;
        List<CarNumber> byLetterPart;

        if (isAllNumbersInRegionWhereUsed(region)) {
            throw new NumbersIsOverException("not exists next number that wasn't before");
        }

        do {
            letterPart = RandomStringUtils.random(3, availableLetters);
            byLetterPart = findByLetterPart(region, letterPart);
        } while (!isFreeLetterPart(byLetterPart, 3));

        int randNumberPart;
        do {
            randNumberPart = RandomUtils.randomIntInclude(0, 999);
        } while (!isFreeNumberPart(byLetterPart, randNumberPart));

        return create(randNumberPart, letterPart, region);
    }

    private boolean isFreeNumberPart(List<CarNumber> byLetterPart, int numberPart) {
        if (CollectionUtils.isEmpty(byLetterPart)) return true;
        return byLetterPart.stream()
                .map(CarNumber::getNumberPart)
                .noneMatch(Predicate.isEqual(numberPart));
    }

    /**
     * create default car number А000АА 116 RUS
     * @return created car number
     */
    public CarNumber create(Region region) {
        return create(0, "ААА", region);
    }

    /**
     * create car number by params. A111AA XXX RUS. X - region code
     * @param numberPart as 1 in description
     * @param letterPart as A in description
     * @param region number region
     * @return created car number
     */
    public CarNumber create(int numberPart, String letterPart, Region region) {
        return CarNumber.builder()
                .letterPart(letterPart)
                .numberPart(numberPart)
                .region(region)
                .created(LocalDateTime.now())
                .build();
    }

    /**
     * detect if letter part is not fulfilled in database
     * @param listByLetterPart list of car numbers extracted by letter part
     * @param numPartPlaces count of numeric digits in number
     * @return is free or not
     */
    public boolean isFreeLetterPart(List<CarNumber> listByLetterPart, int numPartPlaces) {
        if (CollectionUtils.isEmpty(listByLetterPart)) return true;
        return listByLetterPart.size() == Math.pow(10, numPartPlaces);
    }

    public List<CarNumber> findByLetterPart(Region region, String letterPart) {
        if (StringUtils.isEmpty(letterPart) || Objects.isNull(region)) return new ArrayList<>();
        return repository.findByLetterPartAndRegionOrderByLetterPartAsc(letterPart, region);
    }
}
