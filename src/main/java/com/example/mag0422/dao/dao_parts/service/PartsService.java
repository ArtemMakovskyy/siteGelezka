package com.example.mag0422.dao.dao_parts.service;

import com.example.mag0422.entity.parts.HDD_DB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PartsService<T> {
    public List<T> getAll();

    public void save(T obj);

    public T findById(long id);

    public void deleteById(long id);

    public Page<T> findPaginatedFourFilds(int pageNo, int pageSize, String sortField, String sortDirection);

    public Page<T> findPaginatedByPageNPageAize(int pageNo, int pageSize);

    public Page<T> findAtPage(int pageIndex, int pageNumber, Sort.Direction direction, String sortedField);

    public void rememberObject(Long id);

    public T getRememberedObject();

    public void clearRememberedObject();

    public List<T>findAllTextFields(String serchingText);





}

