/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import ug.monografico32.dao.AsignaturaRepository;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Asignatura;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Docente;

/**
 *
 * @author Administrador
 */
@Controller
@SessionAttributes({"curso", "asignaturas", "instructores"})
@RequestMapping( path = "/clase")
public class ClaseController {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private DocenteRepository docenteRepository;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @GetMapping( path = "/agregar/{curso}")
    public String agregarClase(Model model, @PathVariable Curso curso){
        model.addAttribute( curso );
        model.addAttribute("asignaturas", asignaturaRepository.findAll());
        model.addAttribute("instructores", docenteRepository.findAll());
        model.addAttribute( new Clase());
        return "clase/agregar-clase";
    }
    
    @PostMapping( path = "/agregar/{curso}" )
    public String procesarClase( @Valid Clase clase, BindingResult bindingResult,
                                 @ModelAttribute Curso curso, 
                                 /*@ModelAttribute List<Docente> instructores,
                                 @ModelAttribute List<Asignatura> asignaturas,*/
                                 SessionStatus status){
        if( bindingResult.hasErrors() ){
            return "clase/agregar-clase";
        }
        curso.agregarClase(clase);
        status.setComplete();
        return "";
    }
    
    
}
