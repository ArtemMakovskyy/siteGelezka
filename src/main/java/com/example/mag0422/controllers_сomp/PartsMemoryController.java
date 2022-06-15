package com.example.mag0422.controllers_сomp;


import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.MemoryDB;
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
@RequestMapping("/comp_parts/memory")
public class PartsMemoryController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<MemoryDB> memoryService;
    private final String NAME_FOLDER = "parts/memory/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String memoryCreateForm(
            @ModelAttribute("memory") MemoryDB memory_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String memoryCreatePost(
            @ModelAttribute("memory") @Valid MemoryDB memory_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("memoryCreatePost");
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
        memory_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        memoryService.save(memory_db);
        return "redirect:/comp_parts/memory/create";
    }

    @GetMapping("/delete/{position}")
    public String memoryDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete memory " + position);
        memoryService.deleteById(position);
        return memoryViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String memoryDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("memoryDetailPosition");

        model.addAttribute("memory", memoryService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String memoryEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit memory form" + position);
        memoryService.rememberObject(position);
        partCodeService.rememberObjectCode(memoryService.findById(position).getPartCode().getId());
        MemoryDB memory_db = memoryService.findById(position);
        PartCode partCode = partCodeService.findById(memory_db.getPartCode().getId());
        model.addAttribute("memory", memory_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String memoryCreateEditPost(
            @ModelAttribute("memory") @Valid MemoryDB memory_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String memoryCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        memory_db.setId(memoryService.getRememberedObject().getId());
        memory_db.setPartCode(memoryService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        memoryService.save(memory_db);
        partCodeService.save(codeDB);
        memoryService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/memory/create";
    }

    @GetMapping("/show")
    public String memorylistFirstPage(Model model) {
        return memoryViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String memorylistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/memory/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return memoryViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String memoryViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<MemoryDB> page1 = memoryService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<MemoryDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String memorySerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return memorySerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String memorySerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<MemoryDB> page1 = memoryService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<MemoryDB> allTextFields = memoryService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<MemoryDB>() {
            @Override
            public int compare(MemoryDB o1, MemoryDB o2) {
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
