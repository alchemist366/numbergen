package com.aisalin.numbergen.components;

import com.aisalin.numbergen.models.CarNumber;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class NumberStorage {
    private CarNumber carNumber;

}
