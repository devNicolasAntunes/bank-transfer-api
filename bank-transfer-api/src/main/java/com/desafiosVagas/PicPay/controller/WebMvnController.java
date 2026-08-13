package com.desafiosVagas.PicPay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMvnController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }
}
