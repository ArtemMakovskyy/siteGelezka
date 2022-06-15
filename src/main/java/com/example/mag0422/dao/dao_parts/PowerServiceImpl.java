package com.example.mag0422.dao.dao_parts;

import com.example.mag0422.dao.dao_parts.repository.PowerDBRepository;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.PowerDB;
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
public class PowerServiceImpl implements PartsService<PowerDB> {

    private final PowerDBRepository repository;
    private PowerDB db;

    @Autowired
    public PowerServiceImpl(PowerDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PowerDB> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(PowerDB db) {
        repository.save(db);
    }

    @Override
    public PowerDB findById(long id) {
        Optional<PowerDB> optional = repository.findById(id);
        PowerDB db = null;
        if (optional.isPresent()) {
            db = optional.get();
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
        return db;
    }

    @Override
    public void deleteById(long id) {
        Optional<PowerDB> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("SSD not found for id :: " + id);
        }
    }

    @Override
    public Page<PowerDB> findPaginatedFourFilds
            (int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<PowerDB> findPaginatedByPageNPageAize
            (int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<PowerDB> findAtPage
            (int pageIndex, int rawCount, Sort.Direction direction,
             String sortedField) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, rawCount, Sort.Direction.ASC, sortedField);
        return repository.findAll(pageRequest);
    }

    @Override
    public void rememberObject(Long id) {
        if (this.db == null) this.db = new PowerDB();
        this.db = findById(id);
    }

    @Override
    public PowerDB getRememberedObject() {
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
    public List<PowerDB> findAllTextFields(String serchingText) {
        if (serchingText == null) {
            System.out.println("нет данных для поиска");
            serchingText = " ";
        }
        String textInLowerCase = serchingText.toLowerCase();
        List<PowerDB> findingList = new ArrayList<>();

        for (PowerDB obj : repository.findAll())
            if ((obj.getName() != null && obj.getName().toLowerCase().contains(textInLowerCase)) |
                    (obj.getSpecification() != null && obj.getSpecification().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getGelezkaPartNumber() != null && obj.getPartCode().getGelezkaPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getBrinePartNumber() != null && obj.getPartCode().getBrinePartNumber().toLowerCase().contains(textInLowerCase)) |
                    (obj.getPartCode().getOriginalPartNumber() != null && obj.getPartCode().getOriginalPartNumber().toLowerCase().contains(textInLowerCase))
            ) findingList.add(obj);
        return findingList;
    }
}
