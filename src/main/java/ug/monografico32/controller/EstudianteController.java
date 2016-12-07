package ug.monografico32.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.model.Estudiante;

import javax.validation.Valid;

/**
 * Created by Jose Elias on 23/11/2016.
 */
@Controller
@RequestMapping( path = "/estudiante")
public class EstudianteController {

    @GetMapping( path = "/agregar")
    public String enrolarEstudiante(Model model){

        model.addAttribute(new Estudiante());
        return "estudiante/agregar-estudiante";
    }

    @PostMapping( path = "/agregar")
    public String procesarEnrolar(@Valid Estudiante estudiante, Errors errors){

        if (errors.hasErrors()){
            return "estudiante/agregar-estudiante";
        }
        return "redirect:/home";
    }
}
