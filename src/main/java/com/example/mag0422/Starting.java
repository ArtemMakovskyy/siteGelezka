package com.example.mag0422;

import com.example.mag0422.dao.dao_comp.*;
import com.example.mag0422.entity.entity_comp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class Starting {
    @Autowired
    Repository_ComputerVendor repository_computerVendor;
    @Autowired
    Repository_ComputerCode repository_computerCode;
    @Autowired
    Repository_ComputerModel repository_computerModel;
    @Autowired
    Repository_ComputerSpecification repository_computerSpecification;
    @Resource
    Repository_Comp repository_comp;
    @Autowired
    Repository_ComputerVendorComp repository_computerVendorComp;

//    @PostConstruct
    public void init() {
//        saveParts();
//        savecomputer();
//        cvc();
//        repository_comp.deleteById(52L);
        System.out.println(repository_comp.findAll());
    }

    private void cvc() {
        ComputerVendorComp computerVendorComp = new ComputerVendorComp();
        repository_computerVendorComp.save(computerVendorComp);
    }

    private void savecomputer() {
        Long id = 52L;
        Comp comp = new Comp();

        repository_comp.save(comp);
        Long lastIdComp = 0L;
        for (Comp c : repository_comp.findAll()) {
            if (c.getId() > lastIdComp) {
                lastIdComp = c.getId();
            }
        }

        Comp compRep = repository_comp.findById(lastIdComp).get();
        System.out.println(lastIdComp);
        System.out.println(repository_comp.findById(lastIdComp).get());

        ComputerVendorComp computerVendorComp = new ComputerVendorComp();
        var computerVendor = repository_computerVendor.findById(9L).get();
        computerVendorComp.setComputerVendor(computerVendor);
        computerVendorComp.setComp(compRep);
        repository_computerVendorComp.save(computerVendorComp);
        Long lastIdCompVendorComp = 0L;
        for (Comp c : repository_comp.findAll()) {
            if (c.getId() > lastIdCompVendorComp) {
                lastIdCompVendorComp = c.getId();
            }
        }
        ComputerVendorComp computerVendorCompRep = repository_computerVendorComp.findById(lastIdCompVendorComp).get();

//        compRep.setComputerVendorComp(computerVendorCompRep);
//        compRep.setComputerCode();
//        compRep.setComputerVendorComp(computerVendorCompRep);
//        compRep.setComputerVendorComp(computerVendorCompRep);










    }

    private void saveParts() {
//        savecode
        ComputerCode computerCode = new ComputerCode();
        computerCode.setGelezkaCode("011_27042022");
        repository_computerCode.save(computerCode);
        //        savecode
        //        savemodel
        repository_computerModel.save(new ComputerModel("Best 11 model"));
        //        savemodel
//save specification
        repository_computerSpecification.save(
                new ComputerSpecification(
                        "fun", "Core i7 9700",
                        "MB",
                        "DDR4-32Gb", "GTX-1080 8Gb"
                        , "ssd-1000", "hdd-2Tb",
                        "CASE", "700W"));
        //save specification
    }

    public void ComputerVendooDB() {

        if (false) {
            Long id = 17L;
            var comp = new Comp();
            var computerCode = repository_computerCode.findById(id).get();
            var computerModel = repository_computerModel.findById(id).get();
            var computerSpecification = repository_computerSpecification.findById(id).get();
            //need do bi
            var computerVendor = repository_computerVendor.findById(6L).get();

            comp.setComputerCode(computerCode);
//            comp.setComputerVendor(computerVendor);
            comp.setComputerModel(computerModel);
            comp.setComputerSpecification(computerSpecification);
            repository_comp.save(comp);

            computerCode.setComp(comp);
            repository_computerCode.save(computerCode);
            computerModel.setComp(comp);
            repository_computerModel.save(computerModel);
            computerSpecification.setComp(comp);
            repository_computerSpecification.save(computerSpecification);
        }

        if (false) {
            ComputerCode computerCode = repository_computerCode.findById(1L).get();
            computerCode.setComp(repository_comp.findById(72L).get());
            repository_computerCode.save(computerCode);
        }

        if (false) {
            repository_computerSpecification.save(
                    new ComputerSpecification(
                            "fun", "core i3 9100",
                            "MB",
                            "DDR4-8Gb", "video"
                            , "ssd-128", "hdd-1Tb",
                            "CASE", "400w"));
        }

        if (false) {
            repository_computerModel.save(new ComputerModel("Best first model"));
        }

        if (false) {
            ComputerCode computerCode = new ComputerCode();
            computerCode.setGelezkaCode("001_25042022");
            repository_computerCode.save(computerCode);
        }

        if (false) {
            List<ComputerVendor> computerVendorList
                    = Arrays.asList(
                    new ComputerVendor("HP"),
                    new ComputerVendor("ASUS"),
                    new ComputerVendor("Vinga"),
                    new ComputerVendor("Apple"),
                    new ComputerVendor("DELL"),
                    new ComputerVendor("Intel"),
                    new ComputerVendor("Lenovo"),
                    new ComputerVendor("Vinga"),
                    new ComputerVendor("Gelezka"));
            repository_computerVendor.saveAll(computerVendorList);
        }
    }
}
