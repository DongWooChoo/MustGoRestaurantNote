package com.mustgorestaurant.must_go_restaurant.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/common")
@Controller
public class ErrorController {

    @GetMapping("/error")
    public String getErrorPage (@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("errorMessage", error);
        return "common/error"; // 403 에러 페이지로 매핑된 뷰
    }
}
