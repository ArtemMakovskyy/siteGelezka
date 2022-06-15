package com.example.mag0422.controllers_сomp;

import com.example.mag0422.dao.brain.UpdatePartsFromBrain;
import com.example.mag0422.dao.dao_parts.service.PartsService;
import com.example.mag0422.excel.brain.ReadExselSaveSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private ReadExselSaveSQL readExselSaveSQL;

    @Autowired
    UpdatePartsFromBrain update;


    @GetMapping("/excel")
    public String showCreatingFormNewComp(Model model) {
        System.out.println("showCreatingFormNewComp");
        return "excel/main";
    }

    @GetMapping("/update_parts")
    public String update_parts() {
        System.out.println("update_parts");

        update.case_();
        update.fun();
        update.gpu();
        update.hdd();
        update.memorry();
        update.motherboard();
        update.power();
        update.processor();
        update.ssd();

        return "comp/create_pc_parts";
    }


}
