package com.example.mag0422.entity.parts.servisePartEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "part_code")
public class PartCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_code_seq")
    @SequenceGenerator(name = "part_code_seq",allocationSize = 2)
    @Column(name = "id", nullable = false)
    private Long id;

    private String brinePartNumber;
    private String gelezkaPartNumber;
    private String originalPartNumber;
}