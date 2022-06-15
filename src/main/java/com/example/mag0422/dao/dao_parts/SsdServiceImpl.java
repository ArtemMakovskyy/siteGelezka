package com.example.mag0422.dao.dao_parts;

import com.example.mag0422.dao.dao_parts.repository.SSD_DBRepository;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.SSD_DB;
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
public class SsdServiceImpl implements PartsService<SSD_DB> {

    private final SSD_DBRepository repository;
    private SSD_DB db;

    @Autowired
    public SsdServiceImpl(SSD_DBRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SSD_DB> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(SSD_DB db) {
        repository.save(db);
    }

    @Override
    public SSD_DB findById(long id) {
        Optional<SSD_DB> optional = repository.findById(id);
        SSD_DB db = null;
        if (optional.isPresent()) {
            db = optional.get();
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
        return db;
    }

    @Override
    public void deleteById(long id) {
        Optional<SSD_DB> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
    }

    @Override
    public Page<SSD_DB> findPaginatedFourFilds
            (int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<SSD_DB> findPaginatedByPageNPageAize
            (int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<SSD_DB> findAtPage
            (int pageIndex, int rawCount, Sort.Direction direction,
             String sortedField) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, rawCount, Sort.Direction.ASC, sortedField);
        return repository.findAll(pageRequest);
    }

    @Override
    public void rememberObject(Long id) {
        if (this.db == null) this.db = new SSD_DB();
        this.db = findById(id);
    }

    @Override
    public SSD_DB getRememberedObject() {
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
    public List<SSD_DB> findAllTextFields(String serchingText) {
        if (serchingText == null) {
            System.out.println("нет данных для поиска");
            serchingText = " ";
        }
        String textInLowerCase = serchingText.toLowerCase();
        List<SSD_DB> findingList = new ArrayList<>();

        for (SSD_DB obj : repository.findAll())
            if ((obj.getName() != null && obj.getName().toLowerCase().contains(textInLowerCase)) |
                    (obj.getSpecification() != null && obj.getSpecification().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getGelezkaPartNumber() != null && obj.getPartCode().getGelezkaPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getBrinePartNumber() != null && obj.getPartCode().getBrinePartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getOriginalPartNumber() != null && obj.getPartCode().getOriginalPartNumber().toLowerCase().contains(textInLowerCase))
            ) findingList.add(obj);
        return findingList;
    }
}
