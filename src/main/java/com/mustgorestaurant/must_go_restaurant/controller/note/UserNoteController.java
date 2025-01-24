package com.mustgorestaurant.must_go_restaurant.controller.note;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/note")
@Controller
public class UserNoteController {

    @GetMapping("/myNote")
    public String selectMtNote(Model model) {
        return "user/note/myNote";
    }
}
