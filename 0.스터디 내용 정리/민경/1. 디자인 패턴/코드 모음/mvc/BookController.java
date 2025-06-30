package org.example.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/book")
    public String getBook(Model model){
        Book book = new Book("Clean Code", "Robert Martin");
        model.addAttribute("book", book);
        return "bookView";
    }
}
