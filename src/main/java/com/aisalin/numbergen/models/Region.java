package com.aisalin.numbergen.models;

import lombok.*;
import org.hibernate.CacheMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Table(name = "regions")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Immutable
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer code;
}
