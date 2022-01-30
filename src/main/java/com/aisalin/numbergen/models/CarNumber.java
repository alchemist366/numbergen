package com.aisalin.numbergen.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "car_numbers")
@Entity
@Getter
@Setter
@Builder
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
}
