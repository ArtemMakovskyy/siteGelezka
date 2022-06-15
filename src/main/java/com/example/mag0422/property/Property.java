package com.example.mag0422.property;

import com.example.mag0422.entity.brain.Brain;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Property {
    private int viewPositionsOnPage;
    private List<Brain>brainInformationList;
}
