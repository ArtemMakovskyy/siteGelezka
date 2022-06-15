package com.example.mag0422.dao.dao_comp;

import com.example.mag0422.entity.entity_comp.Comp;
import org.springframework.data.repository.CrudRepository;

public interface Repository_Comp extends CrudRepository<Comp,Long> {

//    @Query("SELECT new com.example.mag0422.dao.Repository_Comp(comp.computerCode) "
//            + "FROM Comp comp INNER JOIN comp.computerCode")
//    List<Comp> fetchEmpDeptDataInnerJoin();
}
