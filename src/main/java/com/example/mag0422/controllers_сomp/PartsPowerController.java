package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.PowerDB;
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
@RequestMapping("/comp_parts/power")
public class PartsPowerController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<PowerDB> powerService;
    private final String NAME_FOLDER = "parts/power/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String powerCreateForm(
            @ModelAttribute("power") PowerDB power_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String powerCreatePost(
            @ModelAttribute("power") @Valid PowerDB power_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("powerCreatePost");
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
        power_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        powerService.save(power_db);
        return "redirect:/comp_parts/power/create";
    }

    @GetMapping("/delete/{position}")
    public String powerDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete power " + position);
        powerService.deleteById(position);
        return powerViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String powerDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("powerDetailPosition");

        model.addAttribute("power", powerService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String powerEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit power form" + position);
        powerService.rememberObject(position);
        partCodeService.rememberObjectCode(powerService.findById(position).getPartCode().getId());
        PowerDB power_db = powerService.findById(position);
        PartCode partCode = partCodeService.findById(power_db.getPartCode().getId());
        model.addAttribute("power", power_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String powerCreateEditPost(
            @ModelAttribute("power") @Valid PowerDB power_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String powerCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        power_db.setId(powerService.getRememberedObject().getId());
        power_db.setPartCode(powerService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        powerService.save(power_db);
        partCodeService.save(codeDB);
        powerService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/power/create";
    }

    @GetMapping("/show")
    public String powerlistFirstPage(Model model) {
        return powerViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String powerlistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/power/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return powerViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String powerViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<PowerDB> page1 = powerService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<PowerDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String powerSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return powerSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String powerSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<PowerDB> page1 = powerService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<PowerDB> allTextFields = powerService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<PowerDB>() {
            @Override
            public int compare(PowerDB o1, PowerDB o2) {
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
