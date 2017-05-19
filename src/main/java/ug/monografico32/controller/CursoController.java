package ug.monografico32.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ug.monografico32.dao.PeriodoRepository;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Curso;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Docente;
import ug.monografico32.model.Periodo;

/**
 * Created by Jose Elias on 20/12/2016.
 */
@Controller
@SessionAttributes("docentes")
@RequestMapping(path = "/curso")
public class CursoController {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PeriodoRepository periodoRepository;


    @GetMapping(path = "/agregar")
    public String agregarCurso(Model model){
        
        model.addAttribute( new Curso() );
        model.addAttribute("docentes", docenteRepository.findAll() );
        model.addAttribute("periodos", periodoRepository.findAll() );
        return "curso/agregar-curso";
    }

    @PostMapping(path = "/agregar")
    public String procesarCurso(@Valid Curso curso, BindingResult bindingResult,
                                Model model, @ModelAttribute("docentes") List<Docente> docentes,
                                SessionStatus sessionStatus){

        if ( bindingResult.hasErrors() ){
            return "curso/agregar-curso";
        }

        curso = cursoRepository.save(curso);
        sessionStatus.setComplete();
        return "redirect:"+curso.getId();
    }

    @GetMapping(path = "/{id}")
    public String verDetalle(@PathVariable("id") Long cursoId, Model model){

        Curso curso = cursoRepository.findByIdAndFetchHorario(cursoId);
        model.addAttribute(curso);
        return "curso/curso-detalle";
    }

    @GetMapping(path = "/{curso}/estudiantes")
    public String verEstudiantes(){
        //TODO: Make this work dude!!
        return "";
    }
    
    @GetMapping(path = "/all")
    public String verTodos(Model model){
        List<Curso>cursos = cursoRepository.findAll();
        
        model.addAttribute(cursos);
        return "curso/ver-todos";
    }

}
