package com.example.mag0422.dao.dao_comp;

import com.example.mag0422.entity.entity_comp.Comp;
import com.example.mag0422.entity.entity_comp.ComputerCode;
import com.example.mag0422.entity.entity_comp.ComputerModel;
import com.example.mag0422.entity.entity_comp.ComputerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompDataBaseRepositoryImpl implements CompDataBaseRepository<Comp, Long> {
    @Autowired
    private Repository_Comp repository_comp;

    @Override
    public void save(Comp entity) {
        if (entity == null) {
            System.out.println("no data");
            return;
        }
        repository_comp.save(entity);
    }

    @Override
    public Optional<Comp> findById(Long id) {
        return repository_comp.findById(id);
    }

    @Override
    public List<Comp> saveAll(List<Comp> saveAll) {
        return null;
    }

//    @Override
//    public Page<Comp> findAtPage(int pageIndex, int rowCount,
//                                 Sort.Direction direction, String field) {
//        PageRequest pageRequest = PageRequest.of(pageIndex, rowCount,
//                Sort.Direction.ASC, "title");
//        return repository_comp.findAll(pageRequest);
//    }

    @Override
    public List<Comp> findSame(Comp comp) {
        return null;
    }

    @Override
    public Long findCreatingSpec(ComputerSpecification entity) {
        for (Comp comp : repository_comp.findAll())
            if (comp.getComputerSpecification().getFun().contains(entity.getFun()) &&
                    comp.getComputerSpecification().getCpu().contains(entity.getCpu()) &&
                    comp.getComputerSpecification().getMotherBoard().contains(entity.getMotherBoard()) &&
                    comp.getComputerSpecification().getMemory().contains(entity.getMemory()) &&
                    comp.getComputerSpecification().getGpu().contains(entity.getGpu()) &&
                    comp.getComputerSpecification().getSsd().contains(entity.getSsd()) &&
                    comp.getComputerSpecification().getHdd().contains(entity.getHdd()) &&
                    comp.getComputerSpecification().getCasePc().contains(entity.getCasePc()) &&
                    comp.getComputerSpecification().getPower().contains(entity.getPower())
            ) return comp.getComputerSpecification().getId();
        return null;
    }

    @Override
    public Long findCreatingCode(ComputerCode entity) {
        for (Comp comp : repository_comp.findAll()) {
            if (comp.getComputerCode().getGelezkaCode().contains(entity.getGelezkaCode())
                    && comp.getComputerCode().getBrainCode().contains(entity.getBrainCode())
            ) {
                return comp.getComputerCode().getId();
            }
        }
        return null;
    }

    @Override
    public Long findCreatingModel(ComputerModel entity) {
        for (Comp comp : repository_comp.findAll())
            if (comp.getComputerModel().getModel().contains(entity.getModel())
            ) return comp.getComputerModel().getId();
        return null;
    }


    @Override
    public List<Comp> findByProcessor(String nameProcessor) {
        List<Comp> compList = new ArrayList<>();
        for (Comp comp : repository_comp.findAll()) {
            if (comp.getComputerSpecification().getCpu().contains(nameProcessor)) {
                System.out.println(comp.getComputerSpecification().getComp());
                compList.add(comp);
            }
        }
        return compList;
    }

    @Override
    public List<Comp> findByGpu(String nameGpu) {
        List<Comp> compList = new ArrayList<>();
        for (Comp comp : repository_comp.findAll()) {
            if (comp.getComputerSpecification().getGpu().contains(nameGpu)) {
                compList.add(comp);
            }
        }
        return compList;
    }

    @Override
    public List<Comp> findByCode(String code) {
        List<Comp> compList = new ArrayList<>();
        for (Comp comp : repository_comp.findAll())
            if (comp.getComputerCode().getBrainCode().contains(code) ||
                    comp.getComputerCode().getGelezkaCode().contains(code)) {
                compList.add(comp);
            }
        return compList;
    }

    @Override
    public List<Comp> findByAllPosition(String find_text) {
        List<Comp> compList = new ArrayList<>();
        for (Comp comp : repository_comp.findAll())
            if (comp.getComputerSpecification().getGpu().contains(find_text) ||
                    comp.getComputerSpecification().getHdd().contains(find_text) ||
                    comp.getComputerSpecification().getCpu().contains(find_text) ||
                    comp.getComputerSpecification().getCasePc().contains(find_text) ||
                    comp.getComputerSpecification().getFun().contains(find_text) ||
                    comp.getComputerSpecification().getMemory().contains(find_text) ||
                    comp.getComputerSpecification().getPower().contains(find_text) ||
                    comp.getComputerSpecification().getSsd().contains(find_text) ||
                    comp.getComputerVendorComp().getComputerVendor().getVendor().contains(find_text) ||
                    comp.getComputerModel().getModel().contains(find_text)) compList.add(comp);
        return compList;
    }

    @Override
    public Iterable<Comp> findAll() {
        return repository_comp.findAll();
    }

    @Override
    public int countCompByGPU(String gpu) {
        int count = 0;
        for (Comp comp : repository_comp.findAll())
            if (comp.getComputerSpecification().getGpu().contains(gpu)) count++;
        return count;
    }
}
