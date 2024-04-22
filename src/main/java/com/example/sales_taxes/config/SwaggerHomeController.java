package com.example.sales_taxes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.sales_taxes.utils.RouteConstants.ROOT_ROUTE;

@Slf4j
@Controller
@RequestMapping(ROOT_ROUTE)
public class SwaggerHomeController {

    @GetMapping
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }


}

