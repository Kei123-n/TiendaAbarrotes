package com.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String productos() {
        return "productos"; // nombre de la vista correspondiente
    }
}
