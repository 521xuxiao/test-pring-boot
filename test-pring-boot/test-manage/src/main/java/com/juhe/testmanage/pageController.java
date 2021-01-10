package com.juhe.testmanage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class pageController {
    @RequestMapping("/{pageName}")
    public String toPage(@PathVariable String pageName) {
        return pageName;
    }
}
