package ug.monografico32.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ug.monografico32.model.Curso;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.dao.DocenteRepository;

/**
 * Created by Jose Elias on 20/12/2016.
 */
@Controller
@RequestMapping(path = "/curso")
public class CursoController {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private DocenteRepository docenteRepository;


    @GetMapping(path = "/agregar")
    public String agregarCurso(Model model){
        
        model.addAttribute( new Curso() );
        model.addAttribute("docentes", docenteRepository.findAll() );
        return "curso/agregar-curso";
    }

    @PostMapping(path = "/agregar")
    public String procesarCurso(@Valid Curso curso, BindingResult bindingResult,
                                Model model){

        /*System.out.println("docenteId: "+docenteId);
        System.out.println( "Es Long:? " + (docenteId instanceof Long));*/

        if ( bindingResult.hasErrors() ){
            /*System.out.println("Errors: ");
            for(ObjectError err : bindingResult.getAllErrors()){
                System.out.println(err.toString());
            }*/
            model.addAttribute("docentes", docenteRepository.findAll() );
            return "curso/agregar-curso";
        }
        curso = cursoRepository.save(curso);
        return "redirect:"+curso.getId();
    }

    @GetMapping(path = "/{id}")
    public String verDetalle(@PathVariable Long id, Model model){
        Curso curso = cursoRepository.findById(id);

        model.addAttribute(curso);
        return "curso/curso-detalle";
    }
    
    @GetMapping(path = "/all")
    public String verTodos(Model model){
        List<Curso>cursos = cursoRepository.findAll();
        
        model.addAttribute(cursos);
        return "curso/ver-todos";
    }
}
