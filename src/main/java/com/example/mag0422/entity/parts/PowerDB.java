package com.example.mag0422.entity.parts;

import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "power_db")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PowerDB {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "power_seq")
    @SequenceGenerator(name = "power_seq",allocationSize = 5)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(min = 2, max = 255, message = "Длина от 2 до 255")
    private String name;
    private String specification;
    private int warranty;

    private double price;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "part_code_id")
    private PartCode partCode;
}