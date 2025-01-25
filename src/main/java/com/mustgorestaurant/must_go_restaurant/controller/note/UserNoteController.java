package com.mustgorestaurant.must_go_restaurant.controller.note;

import ch.qos.logback.core.model.Model;
import com.mustgorestaurant.must_go_restaurant.common.annotation.AuthUser;
import com.mustgorestaurant.must_go_restaurant.entity.user.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/note")
@Controller
public class UserNoteController {

    @GetMapping("/myNote")
    public String selectMtNote(@AuthUser UserInfo userinfo, Model model) {
        userinfo.getUserId();
        return "user/note/myNote";
    }
}
