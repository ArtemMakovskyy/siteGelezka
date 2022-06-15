package com.example.mag0422.controllers_сomp;


import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.entity.parts.CaseDB;
import com.example.mag0422.entity.parts.GpuDB;
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
@RequestMapping("/comp_parts/gpu")
public class PartsGpuController extends Property {

    @Autowired
    private PartCodeService partCodeService;
    @Autowired
    private PartsService<GpuDB> gpuService;
    private final String NAME_FOLDER = "parts/gpu/";
    {
        setViewPositionsOnPage(10);
    }
    @GetMapping("/create")
    public String gpuCreateForm(
            @ModelAttribute("gpu") GpuDB gpu_db,
            @ModelAttribute("code") PartCode partCode,
            Model model) {
        boolean codeDuplication = false;
        model.addAttribute("codeDuplication", codeDuplication);
        return NAME_FOLDER + "create";
    }

    @PostMapping("/create")
    public String gpuCreatePost(
            @ModelAttribute("gpu") @Valid GpuDB gpu_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode partCode,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("gpuCreatePost");
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
        gpu_db.setPartCode(partCodeService.findById(
                partCodeService.serchPositionOfCCode(partCode)));
        gpuService.save(gpu_db);
        return "redirect:/comp_parts/gpu/create";
    }

    @GetMapping("/delete/{position}")
    public String gpuDeletePosition(
            @PathVariable(value = "position") int position,
            Model model) {
        System.out.println("delete gpu " + position);
        gpuService.deleteById(position);
        return gpuViewPagePag(1, model);
    }

    @GetMapping("/detail/{position}")
    public String gpuDetailPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("gpuDetailPosition");

        model.addAttribute("gpu", gpuService.findById(position));
        model.addAttribute("position", position);
        return NAME_FOLDER + "detail";
    }

    @GetMapping("/edit/{position}")
    public String gpuEditPosition(
            @PathVariable(value = "position") long position,
            Model model) {
        System.out.println("edit gpu form" + position);
        gpuService.rememberObject(position);
        partCodeService.rememberObjectCode(gpuService.findById(position).getPartCode().getId());
        GpuDB gpu_db = gpuService.findById(position);
        PartCode partCode = partCodeService.findById(gpu_db.getPartCode().getId());
        model.addAttribute("gpu", gpu_db);
        model.addAttribute("code", partCode);

        return NAME_FOLDER + "edit";
    }

    @PostMapping("/edit")
    public String gpuCreateEditPost(
            @ModelAttribute("gpu") @Valid GpuDB gpu_db,
            BindingResult bindingResult1,
            @ModelAttribute("code") PartCode codeDB,
            BindingResult bindingResult2,
            Model model) {
        System.out.println("public String gpuCreateEditPost.");
        //вывод ошибок(валидация)
        if (bindingResult1.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        if (bindingResult2.hasErrors()) {
            return NAME_FOLDER + "create";
        }
        //вставиди null
        partCodeService.checkData(codeDB);


        gpu_db.setId(gpuService.getRememberedObject().getId());
        gpu_db.setPartCode(gpuService.getRememberedObject().getPartCode());

        codeDB.setId(partCodeService.getRememberedObjectCode().getId());

        gpuService.save(gpu_db);
        partCodeService.save(codeDB);
        gpuService.clearRememberedObject();
        partCodeService.clearRememberedObjectCode();
        return "redirect:/comp_parts/gpu/create";
    }

    @GetMapping("/show")
    public String gpulistFirstPage(Model model) {
        return gpuViewPagePag(1, model);
    }


    @PostMapping("/showPos")
    public String gpulistFirstPageWithShowRows(Model model, @RequestParam int countRows) {
        System.out.println("/gpu/showPages: " + countRows);
        this.setViewPositionsOnPage(countRows);
        return gpuViewPagePag(1, model);
    }

    @GetMapping("/show/page/{pageNumber}")
    public String gpuViewPagePag(
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {
        int pageSize = this.getViewPositionsOnPage();

        Page<GpuDB> page1 = gpuService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<GpuDB> list = page1.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("totalItems", page1.getTotalElements());
        model.addAttribute("list", list);
        model.addAttribute("textSerch", new String());
        return NAME_FOLDER + "show";
    }

    @PostMapping("/serch/page")
    public String gpuSerchShort(
            @RequestParam String text,
            Model model) {
        System.out.println(text);
        return gpuSerch(text, 1, model);
    }

    @PostMapping("/serch/page/{pageNumber}")
    public String gpuSerch(
            @RequestParam String text,
            @PathVariable(value = "pageNumber") int pageNumber,
            Model model) {

        int pageSize = 100;

        Page<GpuDB> page1 = gpuService.findAtPage(
                pageNumber, pageSize, Sort.Direction.ASC, "price");

        List<GpuDB> allTextFields = gpuService.findAllTextFields(text);

        Collections.sort(allTextFields, new Comparator<GpuDB>() {
            @Override
            public int compare(GpuDB o1, GpuDB o2) {
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
