package ru.edu.springweb.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainInfoController {

    @GetMapping("/author")
    public String author(Model model) {
        model.addAttribute("author", "Лунин Артем Владимирович");
        return "author";
    }

    @GetMapping("/hobby")
    public String hobby() {
        return "hobby";
    }
}