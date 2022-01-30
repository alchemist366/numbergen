package com.aisalin.numbergen.models;

import lombok.*;

import javax.persistence.*;

@Table(name = "regions")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer code;
}
