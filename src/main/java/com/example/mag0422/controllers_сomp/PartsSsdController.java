package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.SSD_DB;
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
@RequestMapping("/comp_parts/ssd")
public class PartsSsdController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<SSD_DB> ssdService;
    private final String NAME_FOLDER = "parts/ssd/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String ssdCreateForm(
            @ModelAttribute("ssd") SSD_DB ssd_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String ssdCreatePost(
            @ModelAttribute("ssd") @Valid SSD_DB ssd_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("ssdCreatePost");
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
        ssd_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        ssdService.save(ssd_db);
        return "redirect:/comp_parts/ssd/create";
    }

    @GetMapping("/delete/{position}")
    public String ssdDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete ssd " + position);
        ssdService.deleteById(position);
        return ssdViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String ssdDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("ssdDetailPosition");

        model.addAttribute("ssd", ssdService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String ssdEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit ssd form" + position);
        ssdService.rememberObject(position);
        partCodeService.rememberObjectCode(ssdService.findById(position).getPartCode().getId());
        SSD_DB ssd_db = ssdService.findById(position);
        PartCode partCode = partCodeService.findById(ssd_db.getPartCode().getId());
        model.addAttribute("ssd", ssd_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String ssdCreateEditPost(
            @ModelAttribute("ssd") @Valid SSD_DB ssd_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String ssdCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        ssd_db.setId(ssdService.getRememberedObject().getId());
        ssd_db.setPartCode(ssdService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        ssdService.save(ssd_db);
        partCodeService.save(codeDB);
        ssdService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/ssd/create";
    }

    @GetMapping("/show")
    public String ssdlistFirstPage(Model model) {
        return ssdViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String ssdlistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/ssd/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return ssdViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String ssdViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<SSD_DB> page1 = ssdService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<SSD_DB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String ssdSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return ssdSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String ssdSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<SSD_DB> page1 = ssdService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<SSD_DB> allTextFields = ssdService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<SSD_DB>() {
            @Override
            public int compare(SSD_DB o1, SSD_DB o2) {
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
