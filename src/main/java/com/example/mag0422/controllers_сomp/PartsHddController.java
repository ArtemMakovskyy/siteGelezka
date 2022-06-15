package com.example.mag0422.controllers_сomp;


import com.example.mag0422.dao.dao_parts.service.PartsService;

import com.example.mag0422.entity.parts.HDD_DB;
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
@RequestMapping("/comp_parts/hdd")
public class PartsHddController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<HDD_DB> hddService;
    private final String NAME_FOLDER = "parts/hdd/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String hddCreateForm(
            @ModelAttribute("hdd") HDD_DB hdd_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String hddCreatePost(
            @ModelAttribute("hdd") @Valid HDD_DB hdd_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("hddCreatePost");
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
        hdd_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        hddService.save(hdd_db);
        return "redirect:/comp_parts/hdd/create";
    }

    @GetMapping("/delete/{position}")
    public String hddDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete hdd " + position);
        hddService.deleteById(position);
        return hddViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String hddDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("hddDetailPosition");

        model.addAttribute("hdd", hddService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String hddEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit hdd form" + position);
        hddService.rememberObject(position);
        partCodeService.rememberObjectCode(hddService.findById(position).getPartCode().getId());
        HDD_DB hdd_db = hddService.findById(position);
        PartCode partCode = partCodeService.findById(hdd_db.getPartCode().getId());
        model.addAttribute("hdd", hdd_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String hddCreateEditPost(
            @ModelAttribute("hdd") @Valid HDD_DB hdd_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String hddCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        hdd_db.setId(hddService.getRememberedObject().getId());
        hdd_db.setPartCode(hddService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        hddService.save(hdd_db);
        partCodeService.save(codeDB);
        hddService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/hdd/create";
    }

    @GetMapping("/show")
    public String hddlistFirstPage(Model model) {
        return hddViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String hddlistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/hdd/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return hddViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String hddViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<HDD_DB> page1 = hddService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<HDD_DB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String hddSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return hddSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String hddSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<HDD_DB> page1 = hddService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<HDD_DB> allTextFields = hddService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<HDD_DB>() {
            @Override
            public int compare(HDD_DB o1, HDD_DB o2) {
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
