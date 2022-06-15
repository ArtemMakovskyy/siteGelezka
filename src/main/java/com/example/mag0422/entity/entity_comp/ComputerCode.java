package com.example.mag0422.entity.entity_comp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "computer_code")
public class ComputerCode {
    private static final String SEQ_MAME = "computer_code_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME, allocationSize = 5)
    private Long id;
    private String gelezkaCode;
    private String brainCode;
//    https://javascopes.com/hibernate-fetchmode-6edf7210/

    @OneToOne
    @JoinColumn(name = "comp_id")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size=10)
    private Comp comp;

    public ComputerCode(String gelezkaCode) {
        this.gelezkaCode = gelezkaCode;
    }

    public void setGelezkaCode(String gelezkaCode) {
        if (brainCode == null) this.brainCode = "non";
        this.gelezkaCode = gelezkaCode;
    }

    public void setBrainCode(String brainCode) {
        if (gelezkaCode == null) this.gelezkaCode = "non";
        this.brainCode = brainCode;
    }

    public void setComp(Comp comp) {
        this.comp = comp;
    }

    @Override
    public String toString() {
        return "Code=" + gelezkaCode + " " + brainCode+ "";
    }
}
