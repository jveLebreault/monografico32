package ug.monografico32.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ug.monografico32.dao.EstudianteRepository;
import ug.monografico32.dao.PeriodoRepository;
import static ug.monografico32.dao.specification.CursoSpecification.*;
import static ug.monografico32.dao.specification.EstudianteSpecification.byNombresApellidosNotInCurso;

import ug.monografico32.model.*;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.dao.DocenteRepository;

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

    @Autowired
    private EstudianteRepository estudianteRepository;


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
        model.addAttribute("sessionsByDay", curso.getHorario().getSesionsByDayOfWeek());
        return "curso/curso-detalle";
    }

    @GetMapping(path = "/{curso}/estudiantes")
    public String verEstudiantes(@PathVariable Long curso, Model model){

        if( model.containsAttribute("curso"))
            return "curso/curso-estudiante";

        model.addAttribute("curso", cursoRepository.findByIdAndFetchEstudiantes(curso));
        return "curso/curso-estudiante";
    }

    @GetMapping(path = "/{curso}/estudiantes/agregar")
    public String agregarEstudiantes(@PathVariable Curso curso, Estudiante estudiante, Model model, Pageable pageable){

        Page<Estudiante> estudiantes = estudianteRepository.findAll(byNombresApellidosNotInCurso(estudiante, curso), pageable);

        model.addAttribute(new Estudiante());
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute(curso);
        return "curso/agregar-estudiante";
    }

    @PostMapping(path = "/{cursoId}/estudiantes/agregar")
    public String procesarAgregarEstudiante(@PathVariable Long cursoId,
                                            @RequestParam Long estudianteId, RedirectAttributes model){

        Estudiante estudiante = estudianteRepository.findByIdAndFetchCursos(estudianteId);
        Curso curso = cursoRepository.findByIdAndFetchEstudiantes(cursoId);

        if ( estudiante.agregarCurso(curso)) {
            curso.agregarEstudiante(estudiante);
            cursoRepository.save(curso);

            model.addFlashAttribute("cursoAgregado", true);
        }else
            model.addFlashAttribute("cursoAgregado", false);

        return "redirect:/curso/"+curso.getId()+"/estudiantes/agregar";
    }

    @PostMapping(path = "/{cursoId}/estudiantes/eliminar")
    public String eliminarEstudiante(@PathVariable Long cursoId, @RequestParam Long estudianteId, RedirectAttributes model){
        Curso curso = cursoRepository.findByIdAndFetchEstudiantes(cursoId);

        boolean wasRemoved = curso.getEstudiantes().removeIf(e -> e.getId().equals(estudianteId));

        cursoRepository.save(curso);

        model.addFlashAttribute("wasRemoved", wasRemoved);
        model.addFlashAttribute(curso);
        return "redirect:/curso/"+curso.getId()+"/estudiantes";
    }

    @GetMapping(path = "/{cursoId}/clases")
    public String allClasesFromCurso(@PathVariable Long cursoId, Model model){
        Curso curso = cursoRepository.findByIdAndFetchHorario(cursoId);
        model.addAttribute(curso);
        return "curso/curso-clases";
    }
    
    @GetMapping(path = "/all")
    public String verTodos(Model model, Pageable pageable){
        Page<Curso>cursos = cursoRepository.findAll(pageable);

        model.addAttribute(new Curso());
        model.addAttribute("cursos", cursos);
        return "curso/ver-todos";
    }
    
    @GetMapping(path = "/all", params = "encargado")
    public String getCursosByDocente(@RequestParam Long encargado, Model model){
        Stream<Curso> cursoStream = cursoRepository.findByDocenteEncargadoId(encargado);

        Map<Periodo, List<Curso>> periodoCursoMap = groupCursosByPeriodo(cursoStream);

        model.addAttribute("cursosPorPerido", periodoCursoMap);
        return "curso/curso-por-periodo";
    }

    @GetMapping(path = "/all", params = {"encargado","year"})
    public String getCursosByDocente(@RequestParam Long encargado,@RequestParam Integer year ,Model model){

        Stream<Curso> cursoStream;

        if (year == null || year == 0){
            cursoStream = cursoRepository.findByDocenteEncargadoId(encargado);
        } else {
            cursoStream = cursoRepository.findByDocenteEncargadoIdAndPeridoYear(encargado, year);
        }

        Map<Periodo, List<Curso>> periodoCursoMap = groupCursosByPeriodo(cursoStream);

        model.addAttribute("cursosPorPerido", periodoCursoMap);
        return "curso/curso-por-periodo";
    }

    @GetMapping(path = "/all", params = {"nivel","grado","seccion", "year"})
    public String buscarCurso(Curso curso, @RequestParam Integer year, Model model, Pageable pageable){

        Page<Curso> cursos = cursoRepository.findAll(this.createSpecficiation(curso,year), pageable );

        model.addAttribute("cursos", cursos);
        return "curso/ver-todos";
    }

    private Specifications<Curso> createSpecficiation(Curso curso, Integer year){

        return Specifications.where(
                periodoHasYear(year)).and(
                        hasGrado(curso.getGrado())).and(
                                hasNivel(curso.getNivel())).and(
                                        hasSeccion(curso.getSeccion()));
    }
    
    private Map<Periodo, List<Curso>> groupCursosByPeriodo(Stream<Curso> cursoStream){
        Map<Periodo, List<Curso>> periodoCursoMap = cursoStream.
                collect(Collectors.
                        groupingBy(Curso::getPeriodo, HashMap::new, Collectors.toList()));
        
        cursoStream.close();
        return periodoCursoMap;
    }
}
