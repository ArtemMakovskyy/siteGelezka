package com.example.mag0422.controller_thymeleafe;

import com.example.mag0422.entity.entity_thymeleafe.Article;
import com.example.mag0422.entity.entity_thymeleafe.BlogDTO;
import com.example.mag0422.entity.entity_thymeleafe.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/thymeleafe")
public class ArticlesController {
    @Autowired
    private BookService bookService;

    @GetMapping()
    public String indexTl(Model model) {
        return "thymeleafe/thymeleafe";
    }

    @GetMapping("/articles")
    public String allArticles(Model model) {
        model.addAttribute("articles", fetchArticles());
        return "thymeleafe/articles-list";
    }

    private List<Article> fetchArticles() {
        return Arrays.asList(
                new Article("Introduction to Using Thymeleaf in Spring",
                        "https://www.baeldung.com/thymeleaf-in-spring-mvc"
                ),
                new Article("Introduction to Using Thymeleaf in Spring 2",
                        "https://www.baeldung.com/thymeleaf-in-spring-mvc")
        );
    }

    @GetMapping("/blog_dto")
    public String blogDTO(Model model) {
        model.addAttribute("blog_dto", new BlogDTO());
        return "thymeleafe/blog_dto";
    }

    @GetMapping("/ind")
    public String indexForm() {
        return "thymeleafe/templ1";
    }


    @PostMapping("/ind")
    public String index(
            @RequestParam(value = "participant", required = false) String participant,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "id", required = false) Integer id,
            Model model) {
        model.addAttribute("id", id);
        List<Integer> userIds = Arrays.asList(1, 2, 3, 4);
        model.addAttribute("userIds", userIds);
        return "index";
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "thymeleafe/allBooks";
    }
    @PostMapping("/all")
    public String postBook(Model model) {

        return "thymeleafe/allBooks";
    }

    @GetMapping("/arrays")
    public String arrayController(Model model) {
        String[] continents = {
                "Africa", "Antarctica", "Asia", "Australia",
                "Europe", "North America", "Sourth America"
        };

        model.addAttribute("continents", continents);

        return "thymeleafe/continents";
    }

}