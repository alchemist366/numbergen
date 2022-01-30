package com.aisalin.numbergen.models;

import lombok.*;

import javax.persistence.*;

@Table(name = "car_numbers")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarNumber {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "number_part")
    private Integer numberPart;

    @Column(name = "letter_part")
    private String letterPart;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public String getConstPart() {
        return  "116 RUS";
    }
}
