package com.example.mag0422.entity.entity_comp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "computer_vendor")
public class ComputerVendor {
    private static final String SEQ_MAME = "pc_vendor_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME, allocationSize = 5)
    private Long id;
    private String vendor;


    public ComputerVendor(String vendor) {
        this.vendor = vendor;
    }


    @Override
    public String toString() {
        return "" + vendor + "";
    }
}
