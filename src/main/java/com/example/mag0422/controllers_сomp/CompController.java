package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_comp.*;

import com.example.mag0422.entity.entity_comp.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comp")
public class CompController {
    @Autowired
    CompDataBaseRepository compDataBaseRepository;
    @Autowired
    Repository_ComputerCode repository_computerCode;
    @Autowired
    Repository_ComputerModel repository_computerModel;
    @Autowired
    Repository_ComputerSpecification repository_computerSpecification;
    @Autowired
    Repository_ComputerVendorComp repository_computerVendorComp;
    @Autowired
    Repository_Comp repository_comp;
    @Autowired
    Repository_ComputerVendor repository_computerVendor;


    @GetMapping("/create_new")
    public String showCreatingFormNewComp(Model model) {
        model.addAttribute("specification", new ComputerSpecification());
        model.addAttribute("model", new ComputerModel());
        model.addAttribute("code", new ComputerCode());
        return "comp/create_new";
    }

    @PostMapping("/create_new")
    public String createNewComp(
            @ModelAttribute("specification") ComputerSpecification computerSpecification,
            @RequestParam String model
            , @RequestParam String code) {


        // save comp
        Comp compWithoutSets = new Comp();
        compDataBaseRepository.save(compWithoutSets);
        Long lastIdComp = 0L;
        for (Comp c : repository_comp.findAll())
            if (c.getId() > lastIdComp) lastIdComp = c.getId();
        Comp compWithCodeSpecModelINDB = repository_comp.findById(lastIdComp).get();

        //save ComputerSpecification
        computerSpecification.setComp(compWithoutSets);
        repository_computerSpecification.save(computerSpecification);
        Long lastIdCompSpec = 0L;
        for (ComputerSpecification c : repository_computerSpecification.findAll())
            if (c.getId() > lastIdCompSpec) lastIdCompSpec = c.getId();
        System.out.println(lastIdCompSpec);
        ComputerSpecification computerSpecificationINDB = repository_computerSpecification.findById(lastIdCompSpec).get();
        System.out.println(computerSpecificationINDB);

        //save ComputerCode
        ComputerCode computerCode = new ComputerCode();
        computerCode.setComp(compWithoutSets);
        computerCode.setGelezkaCode(code);
        repository_computerCode.save(computerCode);
        Long lastIdCompCode = 0L;
        for (ComputerCode c : repository_computerCode.findAll())
            if (c.getId() > lastIdCompCode) lastIdCompCode = c.getId();
        System.out.println(lastIdCompCode);
        ComputerCode computerCodeINDB = repository_computerCode.findById(lastIdCompCode).get();
        System.out.println(repository_computerCode);

        //save ComputerModel
        ComputerModel computerModel = new ComputerModel(model);
        computerModel.setComp(compWithoutSets);
        repository_computerModel.save(computerModel);
        Long lastIdCompModel = 0L;
        for (ComputerModel c : repository_computerModel.findAll())
            if (c.getId() > lastIdCompModel) lastIdCompModel = c.getId();
        System.out.println(lastIdCompModel);
        ComputerModel computerModelINDB = repository_computerModel.findById(lastIdCompModel).get();
        System.out.println(computerModelINDB);

        //save ComputerVenorComp
        ComputerVendorComp computerVendorComp = new ComputerVendorComp();
        computerVendorComp.setComp(compWithCodeSpecModelINDB);
        computerVendorComp.setComputerVendor(repository_computerVendor.findById(9L).get());
        repository_computerVendorComp.save(computerVendorComp);
        ComputerVendorComp computerVendorCompINDB = new ComputerVendorComp();
        Long lastIdCompVendComp = 0L;
        for (ComputerVendorComp c : repository_computerVendorComp.findAll())
            if (c.getId() > lastIdCompVendComp) lastIdCompVendComp = c.getId();
        computerVendorCompINDB = repository_computerVendorComp.findById(lastIdCompVendComp).get();

        //Set in Comp and save
        compWithoutSets.setComputerCode(computerCodeINDB);
        compWithoutSets.setComputerSpecification(computerSpecificationINDB);
        compWithoutSets.setComputerModel(computerModelINDB);
        compWithoutSets.setComputerVendorComp(computerVendorCompINDB);
        compDataBaseRepository.save(compWithoutSets);

        return "comp/operations";
    }

    @GetMapping("/operations")
    public String operations() {
        return "comp/operations";
    }

    @GetMapping("/create_pc_parts")
    public String createPcParts() {
        return "comp/create_pc_parts";
    }


    @GetMapping("/show_all")
    public String showAllComp(Model model) {
        Iterable allList = compDataBaseRepository.findAll();
        model.addAttribute("computerList", allList);
        return "comp/show_all";
    }

    @PostMapping("/findFromCpu")
    public String findCpuCompOutput(@RequestParam String cpu_find, Model model) {
        List byProcessor = compDataBaseRepository.findByProcessor(cpu_find);
        model.addAttribute("computerList", byProcessor);
        return "comp/show_all";
    }

    @PostMapping("/findFromGpu")
    public String findGpuCompOutput(@RequestParam String gpu_find, Model model) {
        List byGpu = compDataBaseRepository.findByGpu(gpu_find);
        model.addAttribute("computerList", byGpu);
        return "comp/show_all";
    }

    @PostMapping("/findFromCode")
    public String findCodeCompOutput(@RequestParam String code, Model model) {
        List byCode = compDataBaseRepository.findByCode(code);
        model.addAttribute("computerList", byCode);
        return "comp/show_all";
    }

    @PostMapping("/findFromAll")
    public String findAllCompOutput(@RequestParam String text, Model model) {
        List byCode = compDataBaseRepository.findByAllPosition(text);
        model.addAttribute("computerList", byCode);
        return "comp/show_all";
    }
}
