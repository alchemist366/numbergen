package com.aisalin.numbergen.components;

import com.aisalin.numbergen.models.CarNumber;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Setter
public class NumberStorage {
    private CarNumber carNumber;

    public CarNumber getCarNumber() {
        if (Objects.isNull(carNumber)) {
            return CarNumber.builder()
                    .letterPart("ААА")
                    .numberPart(0)
                    .build();
        }
        return carNumber;
    }
}
