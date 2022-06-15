package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.*;
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
@RequestMapping("/comp_parts")
public class PartsFunController extends Property {

    @Autowired
    private PartsService<FunDB> funService;
    @Autowired
    private PartCodeService partCodeService;

    private final String NAME_FOLDER = "parts/fun/";
    {
        setViewPositionsOnPage(10);
    }

    @GetMapping("/fun/create")
    public String funCreateForm(
            @ModelAttribute("fun") FunDB funDB,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create_fun";
    }

    @PostMapping("/fun/create")
    public String funCreatePost(
            @ModelAttribute("fun") @Valid FunDB funDB,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("funCreatePost");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create_fun";
        }
        //вставиди null
        partCodeService.checkData(partCode);
        //проверяет на присутствие аналогичного кода, в случае наличия дубликата выводит сообщение
        if ((boolean) partCodeService.serchCodesForEquals(partCode).get(0)) {
            model.addAttribute("codeDuplication", true);
            model.addAttribute("textError", (String) partCodeService.serchCodesForEquals(partCode).get(1));
            return NAME_FOLDER + "create_fun";
        }
        //save code from dataSQL
        partCodeService.save(partCode);
        //find code from dataSQL
        funDB.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        funService.save(funDB);
        return "redirect:/comp_parts/fun/create";
    }


    @GetMapping("/fun/delete/{position}")
    public String funDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete fun " + position);
        funService.deleteById(position);
        return funViewPagePag(1, model);
    }

    @GetMapping("/fun/detail/{position}")
    public String funDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("funDetailPosition");

        model.addAttribute("fun", funService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail_fun";
    }

    @GetMapping("/fun/edit/{position}")
    public String funEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit fun form" + position);
        funService.rememberObject(position);
        partCodeService.rememberObjectCode(funService.findById(position).getPartCode().getId());
        FunDB funDB = funService.findById(position);
        PartCode partCode = partCodeService.findById(funDB.getPartCode().getId());
        model.addAttribute("fun", funDB);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit_fun";
    }

    @PostMapping("/fun/edit")
    public String funCreateEditPost(
            @ModelAttribute("fun") @Valid FunDB funDB,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String funCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create_fun";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create_fun";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        funDB.setId(funService.getRememberedObject().getId());
        funDB.setPartCode(funService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        funService.save(funDB);
        partCodeService.save(codeDB);
        funService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/fun/create";
    }

    @GetMapping("/fun/show")
    public String funlistFirstPage(Model model) {
        return funViewPagePag(1, model);
    }


    @PostMapping("/fun/showPos")
    public String funlistFirstPageWithShowRows(Model model,@RequestParam int countRows) {
        System.out.println("/fun/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return funViewPagePag(1, model);
    }

    @GetMapping("/fun/show/page/{pageNumber}")
    public String funViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<FunDB> page1 = funService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

//        Page<FunDB> page2 = funService.findPaginatedByPageNPageAizeFunDB
//                (pageNumber, pageSize);
//        Page<FunDB> page3 = funService.findPaginatedFourFildsFunDB
//                (1, pageSize, "price", "asc");

        List<FunDB> listFuns = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("listFun", listFuns);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show_fun";
    }
    @PostMapping("/fun/serch/page")
    public String funSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return funSerch(text,1,model);
    }

    @PostMapping("/fun/serch/page/{pageNumber}")
    public String funSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<FunDB> page1 = funService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<FunDB> allTextFields = funService.findAllTextFields(text);
//        Collections.sort(allTextFields, new Comparator<FunDB>() {
//            @Override
//            public int compare(FunDB o1, FunDB o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//        https://www.youtube.com/watch?v=hqIXm7Ontdg
        Collections.sort(allTextFields, new Comparator<FunDB>() {
            @Override
            public int compare(FunDB o1, FunDB o2) {
                return (int) (o1.getPrice() - o2.getPrice());
            }
        });

//        List<FunDB> listFuns = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", allTextFields.size());
        model.addAttribute("listFun", allTextFields);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show_fun";
    }
}
