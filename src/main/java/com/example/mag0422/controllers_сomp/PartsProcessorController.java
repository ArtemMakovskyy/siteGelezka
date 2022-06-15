package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.ProcessorDB;
import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import com.example.mag0422.entity.parts.servisePartEntity.PartCodeService;
import com.example.mag0422.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/comp_parts/processor")
public class PartsProcessorController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<ProcessorDB> processorService;
    private final String NAME_FOLDER = "parts/processor/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String processorCreateForm(
            @ModelAttribute("processor") ProcessorDB processor_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String processorCreatePost(
            @ModelAttribute("processor") @Valid ProcessorDB processor_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("processorCreatePost");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(partCode);
        //проверяет на присутствие аналогичного кода, в случае наличия дубликата выводит сообщение
        if ((boolean) partCodeService.serchCodesForEquals(partCode).get(0)) {
            model.addAttribute("codeDuplication", true);
            model.addAttribute("textError", (String) partCodeService.serchCodesForEquals(partCode).get(1));
            return NAME_FOLDER + "create";
        }
        //save code from dataSQL
        partCodeService.save(partCode);
        //find code from dataSQL
        processor_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        processorService.save(processor_db);
        return "redirect:/comp_parts/processor/create";
    }

    @GetMapping("/delete/{position}")
    public String processorDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete processor " + position);
        processorService.deleteById(position);
        return processorViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String processorDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("processorDetailPosition");

        model.addAttribute("processor", processorService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String processorEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit processor form" + position);
        processorService.rememberObject(position);
        partCodeService.rememberObjectCode(processorService.findById(position).getPartCode().getId());
        ProcessorDB processor_db = processorService.findById(position);
        PartCode partCode = partCodeService.findById(processor_db.getPartCode().getId());
        model.addAttribute("processor", processor_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String processorCreateEditPost(
            @ModelAttribute("processor") @Valid ProcessorDB processor_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String processorCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        processor_db.setId(processorService.getRememberedObject().getId());
        processor_db.setPartCode(processorService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        processorService.save(processor_db);
        partCodeService.save(codeDB);
        processorService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/processor/create";
    }

    @GetMapping("/show")
    public String processorlistFirstPage(Model model) {
        return processorViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String processorlistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/processor/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return processorViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String processorViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<ProcessorDB> page1 =processorService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<ProcessorDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String processorSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return processorSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String processorSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<ProcessorDB> page1 = processorService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<ProcessorDB> allTextFields = processorService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<ProcessorDB>() {
            @Override
            public int compare(ProcessorDB o1, ProcessorDB o2) {
                return (int) (o1.getPrice() - o2.getPrice());
            }
        });

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", allTextFields.size());
        model.addAttribute("list", allTextFields);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }
}
