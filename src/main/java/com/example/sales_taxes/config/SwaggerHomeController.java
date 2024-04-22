package com.example.sales_taxes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class SwaggerHomeController {

    @GetMapping
    public String index() {
        log.info("Redirect to : /swagger-ui/index.html");
        return "redirect:/swagger-ui/index.html";
    }


}
