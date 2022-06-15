package com.example.mag0422.entity.entity_comp;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ComputerVendorComp {
    private static final String SEQ_MAME = "computer_vendor_comp_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME, allocationSize = 5)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comp_id")
    private Comp comp;

    @ManyToOne
    @JoinColumn(name = "computer_vendor_id")
    ComputerVendor computerVendor;
}
