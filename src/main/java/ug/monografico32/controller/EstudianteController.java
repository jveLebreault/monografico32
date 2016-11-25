package ug.monografico32.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jose Elias on 23/11/2016.
 */
@Controller
@RequestMapping( path = "/estudiante")
public class EstudianteController {

    @GetMapping( path = "/agregar")
    public String agregarEstudiante(){
        return "estudiante/agregar-estudiante";
    }
}
