package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.repository.ComputerDBRepository;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.*;
import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import com.example.mag0422.entity.parts.servisePartEntity.PartCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/computer")
public class ComputerDBController {
    private final String NAME_FOLDER = "computer/";

    private ComputerDBRepository computerDBRepository;
    private PartsService<CaseDB> caseService;
    private PartsService<FunDB> funService;
    private PartsService<GpuDB> gpuService;
    private PartsService<HDD_DB> hddService;
    private PartsService<MotherboardDB> mbService;
    private PartsService<MemoryDB> memoryService;
    private PartsService<SSD_DB> ssdService;
    private PartsService<ProcessorDB> processorService;
    private PartsService<PowerDB> powerService;
    private PartCodeService partCodeService;

    @Autowired
    public ComputerDBController(ComputerDBRepository computerDBRepository, PartsService<CaseDB> caseService, PartsService<FunDB> funService, PartsService<GpuDB> gpuService, PartsService<HDD_DB> hddService, PartsService<MotherboardDB> mbService, PartsService<MemoryDB> memoryService, PartsService<SSD_DB> ssdService, PartsService<ProcessorDB> processorService, PartsService<PowerDB> powerService, PartCodeService partCodeService) {
        this.computerDBRepository = computerDBRepository;
        this.caseService = caseService;
        this.funService = funService;
        this.gpuService = gpuService;
        this.hddService = hddService;
        this.mbService = mbService;
        this.memoryService = memoryService;
        this.ssdService = ssdService;
        this.processorService = processorService;
        this.powerService = powerService;
        this.partCodeService = partCodeService;
    }

    @GetMapping("/show")
    public String showComputers(Model model) {
        model.addAttribute("computer", computerDBRepository.findAll());
        return NAME_FOLDER + "show";
    }

    @GetMapping("/create")
    public String createShowFormComputers(
            @ModelAttribute("code") PartCode partCode,
            @ModelAttribute("procrssor") ProcessorDB processorDB,
            @ModelAttribute("motherboard") MotherboardDB motherboardDB,
            @ModelAttribute("memory") MemoryDB memoryDB,
            @ModelAttribute("gpu") GpuDB gpuDB,
            @ModelAttribute("hdd") HDD_DB hdd_db,
            @ModelAttribute("ssd") SSD_DB ssd_db,
            @ModelAttribute("case") CaseDB caseDB,
            @ModelAttribute("power") PowerDB powerDB,
            Model model) {
        return NAME_FOLDER + "create";
    }
}
