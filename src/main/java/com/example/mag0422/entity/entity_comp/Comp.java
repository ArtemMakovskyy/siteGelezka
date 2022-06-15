package com.example.mag0422.entity.entity_comp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "comp")
public class Comp {
    private static final String SEQ_MAME = "comp_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME, allocationSize = 5)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH
                    , CascadeType.MERGE
                    , CascadeType.REFRESH})
    @JoinColumn(name = "computer_code_id")
    private ComputerCode computerCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "computer_model_id")
    private ComputerModel computerModel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "computer_specification_id")
    private ComputerSpecification computerSpecification;

    @OneToOne
    @JoinColumn(name = "computer_vendor_comp_id")
    private ComputerVendorComp computerVendorComp;

    @Override
    public String toString() {
        return
                "id=" + id +
                " " + computerCode +
                " " + computerModel +
                " " + computerSpecification +
                " " + computerVendorComp.getComputerVendor().getVendor()+"\n";
    }
}
