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
@Table(name = "computer_specification")
public class ComputerSpecification {
    private static final String SEQ_MAME = "computer_specification_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_MAME)
    @SequenceGenerator(name = SEQ_MAME, sequenceName = SEQ_MAME, allocationSize = 5)
    private Long id;

    private String fun;
    private String cpu;
    private String motherBoard;
    private String memory;
    private String gpu;
    private String ssd;
    private String hdd;
    private String casePc;
    private String power;

    public ComputerSpecification(String fun, String cpu, String motherBoard, String memory, String gpu, String ssd, String hdd, String casePc, String power) {
        this.fun = fun;
        this.cpu = cpu;
        this.motherBoard = motherBoard;
        this.memory = memory;
        this.gpu = gpu;
        this.ssd = ssd;
        this.hdd = hdd;
        this.casePc = casePc;
        this.power = power;
    }

    @OneToOne
    @JoinColumn(name = "comp_id")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private Comp comp;

    @Override
    public String toString() {
        return
                fun + '\'' +
                        "," + cpu + '\'' +
                        ", " + motherBoard + '\'' +
                        ", " + memory + '\'' +
                        ", " + gpu + '\'' +
                        ", " + ssd + '\'' +
                        ", " + hdd + '\'' +
                        ", " + casePc + '\'' +
                        ", " + power;
    }
}
