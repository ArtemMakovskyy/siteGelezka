package com.example.mag0422.controllers_сomp;


import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.CaseDB;
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
@RequestMapping("/comp_parts/case")
public class PartsCaseController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<CaseDB> caseService;
    private final String NAME_FOLDER = "parts/case/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String caseCreateForm(
            @ModelAttribute("case") CaseDB case_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String caseCreatePost(
            @ModelAttribute("case") @Valid CaseDB case_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("caseCreatePost");
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
        case_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        caseService.save(case_db);
        return "redirect:/comp_parts/case/create";
    }

    @GetMapping("/delete/{position}")
    public String caseDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete case " + position);
        caseService.deleteById(position);
        return caseViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String caseDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("caseDetailPosition");

        model.addAttribute("case", caseService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String caseEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit case form" + position);
        caseService.rememberObject(position);
        partCodeService.rememberObjectCode(caseService.findById(position).getPartCode().getId());
        CaseDB case_db = caseService.findById(position);
        PartCode partCode = partCodeService.findById(case_db.getPartCode().getId());
        model.addAttribute("case", case_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String caseCreateEditPost(
            @ModelAttribute("case") @Valid CaseDB case_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String caseCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        case_db.setId(caseService.getRememberedObject().getId());
        case_db.setPartCode(caseService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        caseService.save(case_db);
        partCodeService.save(codeDB);
        caseService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/case/create";
    }

    @GetMapping("/show")
    public String caselistFirstPage(Model model) {
        return caseViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String caselistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/case/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return caseViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String caseViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<CaseDB> page1 = caseService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<CaseDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String caseSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return caseSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String caseSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<CaseDB> page1 = caseService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<CaseDB> allTextFields = caseService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<CaseDB>() {
            @Override
            public int compare(CaseDB o1, CaseDB o2) {
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
