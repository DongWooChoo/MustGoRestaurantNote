package com.mustgorestaurant.must_go_restaurant.controller.note;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserNoteController {

    @GetMapping("/myNote")
    public String selectMtNote(Model model) {
        int a = 21122;
        return "user/note/myNote";
    }
}
