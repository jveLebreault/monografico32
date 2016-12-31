package ug.monografico32.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.model.Curso;

import javax.validation.Valid;

/**
 * Created by Jose Elias on 20/12/2016.
 */
@Controller
@RequestMapping(path = "/curso")
public class CursoController {


    @GetMapping(path = "/agregar")
    public String agregarCurso(Model model){

        model.addAttribute( new Curso() );
        return "curso/agregar-curso";
    }

    @PostMapping(path = "/agregar")
    public String procesarCurso(@Valid Curso curso, BindingResult bindingResult){

        if ( bindingResult.hasErrors() ){
            return "curso/agregar-curso";
        }
        //TODO: persist curso after succesful creation
        return "curso/curso-detalle";

    }
}
