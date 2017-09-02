/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ug.monografico32.dao.ClaseRepository;
import ug.monografico32.model.*;

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

    @GetMapping( path = "/clase", params = "instructor")
    public String showClasesByInstructor(@RequestParam Docente instructor, Model model,
                                         @SessionAttribute Periodo periodo){

        Stream<Clase> claseStream = claseRepository.
                findByInstructorIdAndPeriodoId(instructor.getId(), periodo.getId());

        Map<DayOfWeek, Set<Sesion>> sesionsByDay = groupSessionsByDay(claseStream);
        
        model.addAttribute("instructor", instructor);
        model.addAttribute(periodo);
        model.addAttribute("sessionsByDay", sesionsByDay);
        return "horario/clases-instructor-periodo";
    }

    private Map<DayOfWeek,Set<Sesion>> groupSessionsByDay(Stream<Clase> claseStream) {
        return claseStream.flatMap(clase -> clase.getSesiones().stream()).
                collect( Collectors.
                        groupingBy( Sesion::getDia, TreeMap::new, Collectors.toSet()));
    }


}
