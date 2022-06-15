package com.example.mag0422.entity.parts.servisePartEntity;

import com.example.mag0422.entity.parts.FunDB;

import java.util.List;

public interface PartCodeService {
    public PartCode checkData(PartCode partCode);

    public Long serchPositionOfCCode(PartCode inerCode);

    public List serchCodesForEquals(PartCode partCode);

    public PartCode findById(Long id);

    public void save(PartCode partCode);

    public void rememberObjectCode(Long id);

    public PartCode getRememberedObjectCode();

    public void clearRememberedObjectCode();

    List<PartCode>findAllTextFields(String serchingText);
}
