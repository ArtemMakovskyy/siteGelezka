package com.example.mag0422.dao.dao_parts;

import com.example.mag0422.dao.dao_parts.repository.ProcessorDBRepository;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.ProcessorDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessorServiceImpl implements PartsService<ProcessorDB> {

    private final ProcessorDBRepository repository;
    private ProcessorDB db;

    @Autowired
    public ProcessorServiceImpl(ProcessorDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProcessorDB> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(ProcessorDB db) {
        repository.save(db);
    }

    @Override
    public ProcessorDB findById(long id) {
        Optional<ProcessorDB> optional = repository.findById(id);
        ProcessorDB db = null;
        if (optional.isPresent()) {
            db = optional.get();
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
        return db;
    }

    @Override
    public void deleteById(long id) {
        Optional<ProcessorDB> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
    }

    @Override
    public Page<ProcessorDB> findPaginatedFourFilds
            (int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<ProcessorDB> findPaginatedByPageNPageAize
            (int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<ProcessorDB> findAtPage
            (int pageIndex, int rawCount, Sort.Direction direction,
             String sortedField) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, rawCount, Sort.Direction.ASC, sortedField);
        return repository.findAll(pageRequest);
    }

    @Override
    public void rememberObject(Long id) {
        if (this.db == null) this.db = new ProcessorDB();
        this.db = findById(id);
    }

    @Override
    public ProcessorDB getRememberedObject() {
        if (this.db == null) {
            System.out.println("Object is null");
            throw new NullPointerException("Object is null");
        }
        return this.db;
    }

    @Override
    public void clearRememberedObject() {
        this.db = null;
    }

    @Override
    public List<ProcessorDB> findAllTextFields(String serchingText) {
        if (serchingText == null) {
            System.out.println("нет данных для поиска");
            serchingText = " ";
        }
        String textInLowerCase = serchingText.toLowerCase();
        List<ProcessorDB> findingList = new ArrayList<>();

        for (ProcessorDB obj : repository.findAll())
            if ((obj.getName() != null && obj.getName().toLowerCase().contains(textInLowerCase)) |
                    (obj.getSpecification() != null && obj.getSpecification().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getGelezkaPartNumber() != null && obj.getPartCode().getGelezkaPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getBrinePartNumber() != null && obj.getPartCode().getBrinePartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getOriginalPartNumber() != null && obj.getPartCode().getOriginalPartNumber().toLowerCase().contains(textInLowerCase))
            ) findingList.add(obj);
        return findingList;
    }
}
