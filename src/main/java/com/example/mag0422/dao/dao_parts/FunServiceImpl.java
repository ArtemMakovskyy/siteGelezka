package com.example.mag0422.dao.dao_parts;

import com.example.mag0422.dao.dao_parts.repository.FunDBRepository;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.FunDB;
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
public class FunServiceImpl implements PartsService<FunDB> {

    private final FunDBRepository repository;
    private FunDB db;

    @Autowired
    public FunServiceImpl(FunDBRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<FunDB> findPaginatedFourFilds
            (int pageNo, int pageSize, String sortField, String sortDirection) {

//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
//                ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<FunDB> findPaginatedByPageNPageAize
            (int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public void rememberObject(Long id) {
        if (this.db == null) this.db = new FunDB();
        this.db = findById(id);
    }

    @Override
    public FunDB getRememberedObject() {
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
    public List<FunDB> findAllTextFields(String serchingText) {
        if (serchingText == null) {
            System.out.println("нет данных для поиска");
            serchingText = " ";
        }
        String textInLowerCase = serchingText.toLowerCase();
        List<FunDB> findingList = new ArrayList<>();

        for (FunDB fun : repository.findAll())
            if ((fun.getName() != null && fun.getName().toLowerCase().contains(textInLowerCase)) |
                    (fun.getName() != null && fun.getSocket().toLowerCase().contains(textInLowerCase)) |
                    (fun.getName() != null && fun.getSpecification().toLowerCase().contains(textInLowerCase)) |
                    (fun.getPartCode().getGelezkaPartNumber() != null && fun.getPartCode().getGelezkaPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (fun.getPartCode().getBrinePartNumber() != null && fun.getPartCode().getBrinePartNumber().toLowerCase().contains(textInLowerCase)) |
                    (fun.getPartCode().getOriginalPartNumber() != null && fun.getPartCode().getOriginalPartNumber().toLowerCase().contains(textInLowerCase))
            ) findingList.add(fun);
        return findingList;
    }


    @Override
    public Page<FunDB> findAtPage
            (int pageIndex, int rawCount, Sort.Direction direction,
             String sortedField) {
        String WorkLink = "https://www.youtube.com/watch?v=4R5t5JzCBTw&list=PLmEQRj1_Mt5f5MlXGlf5kzldb9Rl8Pwuo&index=10";
        String timiPagin = "22:20";
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, rawCount, Sort.Direction.ASC, sortedField);
        return repository.findAll(pageRequest);
    }

    @Override
    public List<FunDB> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(FunDB db) {
        repository.save(db);
    }

    @Override
    public FunDB findById(long id) {
        Optional<FunDB> optional = repository.findById(id);
        FunDB funDB = null;
        if (optional.isPresent()) {
            funDB = optional.get();
        } else {
            throw new RuntimeException("FunDB not found for id :: " + id);
        }
        return funDB;
    }

    @Override
    public void deleteById(long id) {
        Optional<FunDB> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("FunDB not found for id :: " + id);
        }
    }
}
