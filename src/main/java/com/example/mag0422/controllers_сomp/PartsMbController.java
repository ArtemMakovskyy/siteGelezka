package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.MotherboardDB;
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
@RequestMapping("/comp_parts/mb")
public class PartsMbController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<MotherboardDB> mbService;
    private final String NAME_FOLDER = "parts/mb/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String mbCreateForm(
            @ModelAttribute("mb") MotherboardDB mb_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String mbCreatePost(
            @ModelAttribute("mb") @Valid MotherboardDB mb_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("mbCreatePost");
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
        mb_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        mbService.save(mb_db);
        return "redirect:/comp_parts/mb/create";
    }

    @GetMapping("/delete/{position}")
    public String mbDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete mb " + position);
        mbService.deleteById(position);
        return mbViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String mbDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("mbDetailPosition");

        model.addAttribute("mb", mbService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String mbEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit mb form" + position);
        mbService.rememberObject(position);
        partCodeService.rememberObjectCode(mbService.findById(position).getPartCode().getId());
        MotherboardDB mb_db = mbService.findById(position);
        PartCode partCode = partCodeService.findById(mb_db.getPartCode().getId());
        model.addAttribute("mb", mb_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String mbCreateEditPost(
            @ModelAttribute("mb") @Valid MotherboardDB mb_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String mbCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        mb_db.setId(mbService.getRememberedObject().getId());
        mb_db.setPartCode(mbService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        mbService.save(mb_db);
        partCodeService.save(codeDB);
        mbService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/mb/create";
    }

    @GetMapping("/show")
    public String mblistFirstPage(Model model) {
        return mbViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String mblistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/mb/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return mbViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String mbViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<MotherboardDB> page1 = mbService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<MotherboardDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String mbSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return mbSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String mbSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<MotherboardDB> page1 = mbService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<MotherboardDB> allTextFields = mbService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<MotherboardDB>() {
            @Override
            public int compare(MotherboardDB o1, MotherboardDB o2) {
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
