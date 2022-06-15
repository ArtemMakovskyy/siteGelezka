package com.example.mag0422.entity.entity_comp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "computer_model")
public class ComputerModel {
        private static final String SEQ_MAME = "computer_model_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME,allocationSize = 5)
    private Long id;
    private String model;

    @OneToOne
    @JoinColumn(name = "comp_id")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=10)
    private Comp comp;

    public ComputerModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return
                "" + model ;
    }
}
