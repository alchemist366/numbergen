package com.aisalin.numbergen.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "car_numbers")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarNumber {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_part")
    private Integer numberPart;

    @Column(name = "letter_part")
    private String letterPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_code")
    private Region region;

    @Column(name = "created")
    private LocalDateTime created;

    public String getConstPart() {
        return  region.getCode() + " RUS";
    }

    @Override
    public String toString() {
        return "CarNumber{" +
                "id=" + id +
                ", numberPart=" + numberPart +
                ", letterPart='" + letterPart + '\'' +
                ", region=" + region +
                ", created=" + created +
                '}';
    }
}
