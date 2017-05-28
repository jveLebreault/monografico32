package ug.monografico32.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
@SessionAttributes({"docentes","periodos"})
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
    
    @GetMapping(path = "/all", params = "docente")
    public String getCursoByDocente(@RequestParam Long docenteId, Model model){
        Stream<Curso> cursoStream = cursoRepository.findByDocenteEncargadoId(docenteId);
        
        Map<Periodo, List<Curso>> periodoCursoMap = groupCursosByPeriodo(cursoStream);
        
        model.addAttribute("cursosPorPerido", periodoCursoMap);
        return "curso/curso-por-periodo";
    }
    
    private Map<Periodo, List<Curso>> groupCursosByPeriodo(Stream<Curso> cursoStream){
        Map<Periodo, List<Curso>> periodoCursoMap = cursoStream.
                collect(Collectors.
                        groupingBy(Curso::getPeriodo, HashMap::new, Collectors.toList()));
        
        cursoStream.close();
        return periodoCursoMap;
    }
}
