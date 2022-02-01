package com.aisalin.numbergen.models;

import lombok.*;
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

    @Column
    private String name;

    @Id
    @Column
    private Integer code;
}
