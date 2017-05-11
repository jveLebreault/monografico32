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
import ug.monografico32.dao.ClaseRepository;
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
@RequestMapping( path = "/horario")
public class HorarioController {
    
    @Autowired
    private ClaseRepository claseRepository;
    
    @GetMapping( path = "/clase/{claseId}")
    public String verClase(Model model, @PathVariable("claseId") Long id){
        Clase clase = claseRepository.findAndFetchSesiones(id);
        model.addAttribute(clase);
        return "horario/clase-detalle";
    }
    
    
}
