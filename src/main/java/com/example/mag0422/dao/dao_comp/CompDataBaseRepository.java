package com.example.mag0422.dao.dao_comp;


import com.example.mag0422.entity.entity_comp.ComputerCode;
import com.example.mag0422.entity.entity_comp.ComputerModel;
import com.example.mag0422.entity.entity_comp.ComputerSpecification;

import java.util.List;
import java.util.Optional;

public interface CompDataBaseRepository<E, K> {

    void save(E entity);

    Optional<E> findById(K id);

    List<E> saveAll(List<E> saveAll);

    List<E> findSame(E comp);

    List<E> findByProcessor(String nameProcessor);

    List<E> findByGpu(String nameProcessor);

    List<E> findByCode(String code);

    List<E> findByAllPosition(String code);

    Iterable<E> findAll();

    int countCompByGPU(String gpu);

    Long findCreatingModel(ComputerModel entity);

    Long findCreatingSpec(ComputerSpecification entity);

    Long findCreatingCode(ComputerCode entity);


    //    Page<Comp> findAtPage(int pageIndex, int rowCount,
//                          Sort.Direction direction, String field);


    //
//    Optional findById(Object o);
//
//    boolean existsById(Object o);
//

//
//    Iterable findAllById(Iterable iterable);
//
//    long count();
//
//    void deleteById(Object o);
//
//    void delete(Object entity);
//
//    void deleteAllById(Iterable iterable);
//
//    void deleteAll(Iterable entities);
//
//    void deleteAll();
}
