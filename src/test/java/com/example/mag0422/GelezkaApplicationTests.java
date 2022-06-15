package com.example.mag0422;


import com.example.mag0422.dao.dao_parts.repository.FunDBRepository;
import com.example.mag0422.dao.dao_parts.repository.PartCodeRepository;
import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import com.example.mag0422.entity.parts.servisePartEntity.ServicePartCodeImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GelezkaApplicationTests {
    @Autowired
    private ServicePartCodeImpl servicePartCode;
    @Autowired
    private PartCodeRepository partCodeRepository;
    @Autowired
    private FunDBRepository funDBRepository;

    @Test
    void contextLoads() {
        PartCode partCode = partCodeRepository.findById(1L).get();
        String brinePartNumber  = partCode.getBrinePartNumber();
        String gelezkaPartNumber  = partCode.getGelezkaPartNumber();
        String originalPartNumber  = partCode.getOriginalPartNumber();


        List<PartCode> codsList = partCodeRepository.findAll();
        Long positionOfComparison = Long.valueOf(codsList.size() - 1);
        System.out.println(codsList.size());
        System.out.println(positionOfComparison);

//        PartCode lastCodePosition = partCodeRepository.findById(positionOfComparison).get();




    }

}
