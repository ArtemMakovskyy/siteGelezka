package com.example.mag0422.controllers_user;

import com.example.mag0422.dao.dao_user.Repository_User;
import com.example.mag0422.entity.entity_user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private Repository_User repository_user;

    //    @GetMapping("/register")
//    public String createForm(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//
//        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
//        model.addAttribute("listProfession", listProfession);
//        return "user/register_form";
//    }
    @GetMapping("/register")
    public String createForm(@ModelAttribute("user") User user
            ,Model model) {

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);
        return "user/register_form";
    }


    @PostMapping("/register")
    public String create(@ModelAttribute("user") @Valid User user
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors())return "user/register_form";
        repository_user.save(user);
        System.out.println(user);
        return "user/register_success";
    }

}
