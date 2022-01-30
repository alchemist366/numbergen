package com.aisalin.numbergen.converters;

import com.aisalin.numbergen.models.CarNumber;
import org.springframework.stereotype.Component;

@Component
public class CarNumberConverter {
    public String convert(CarNumber carNumber) {
        String letterPart = carNumber.getLetterPart();
        return String.format("%s%03d%s %s",
                letterPart.charAt(0),
                carNumber.getNumberPart(),
                letterPart.substring(1),
                carNumber.getConstPart());
    }
}
