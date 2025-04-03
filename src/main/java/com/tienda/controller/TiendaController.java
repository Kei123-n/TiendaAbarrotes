package com.tienda.controller;

import com.tienda.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class TiendaController {

    @GetMapping("/")
    public String home(Model model) {
        // Crear algunos productos
        List<Producto> productos = Arrays.asList(
            new Producto("Arroz", 10.0, 8),  // Este producto no está por agotarse
            new Producto("Frijoles", 15.5, 2), // Este producto estará por agotarse
            new Producto("Aceite", 20.0, 3)   // Este producto también estará por agotarse
        );

        // Añadir los productos al modelo
        model.addAttribute("productos", productos);

        // Variable para la alerta
        String alerta = null;

        // Verificar si hay productos por agotarse
        for (Producto producto : productos) {
            if (producto.estaPorAgotarse()) {
                alerta = "¡Alerta! El producto " + producto.getNombre() + " está por agotarse.";
                break; // Detenemos el bucle cuando encontramos un producto por agotarse
            }
        }

        // Pasar la alerta al modelo, si existe
        if (alerta != null) {
            model.addAttribute("alerta", alerta);
        }

        return "index"; // Retornar la vista
    }
}
