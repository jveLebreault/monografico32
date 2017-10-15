/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.controller;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ug.monografico32.dao.AsignacionRepository;
import ug.monografico32.dao.ClaseRepository;
import ug.monografico32.dao.EstudianteRepository;
import ug.monografico32.model.*;

import javax.validation.Valid;

@Controller
@SessionAttributes({"estudiantes","claseAsignada"})
@RequestMapping( path = "/clase")
public class ClaseController {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("asignados");
    }

    @GetMapping( path = "/{claseId}")
    public String verClase(Model model, @PathVariable("claseId") Long id){
        Clase clase = claseRepository.findAndFetchSesiones(id);
        model.addAttribute(clase);
        return "horario/clase-detalle";
    }

    @PostMapping(path = "/{claseId}/eliminar", params = "cursoId")
    public String eliminarClase(@PathVariable Long claseId, @RequestParam Long cursoId, Model model){
        Long wasRemoved = claseRepository.deleteById(claseId);
        model.addAttribute("wasRemoved", wasRemoved);
        model.addAttribute("cursoId", cursoId);
        return "redirect:/curso/{cursoId}/clases";
    }

    @GetMapping( params = "instructor")
    public String showClasesByInstructor(@RequestParam Docente instructor, Model model,
                                         @SessionAttribute Periodo periodo){

        List<Clase> clases = claseRepository.findListByDocenteAndPeriodo(instructor, periodo);
        Map<DayOfWeek, List<Sesion>> sesionsByDay = groupSessionsByDay(clases);

        model.addAttribute("instructor", instructor);
        model.addAttribute(periodo);
        model.addAttribute("sessionsByDay", sesionsByDay);

        return "horario/clases-instructor-periodo";
    }

    private Map<DayOfWeek,List<Sesion>> groupSessionsByDay(List<Clase> claseList) {
        return claseList.stream().flatMap(clase -> clase.getSesiones().stream()).
                collect( Collectors.
                        groupingBy( Sesion::getDia, TreeMap::new, Collectors.toList()));
    }

    @GetMapping(path = "/{clase}/asignacion/all")
    public String getAsignaciones(Model model, @PathVariable Clase clase, @AuthenticationPrincipal Usuario usuario){

        List<Asignacion> asignaciones;

        if( usuario.getAuthorities().contains(Roles.ESTUDIANTE)){
            Estudiante estudiante = (Estudiante) usuario;
            asignaciones = asignacionRepository.findByClaseAndAsignadosContains(clase, estudiante);
        }else{
            asignaciones = asignacionRepository.findByClase(clase);
        }

        model.addAttribute(clase);
        model.addAttribute("asignaciones", asignaciones);
        return "horario/asignacion/asignaciones";
    }

    @GetMapping(path = "/{clase}/asignacion/crear")
    public String crearAsignacion(Model model, @PathVariable Clase clase){
        List<Estudiante> estudiantes = estudianteRepository.
                                        findByCursosIn(clase.getHorario().getCurso());

        model.addAttribute(new Asignacion());
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("claseAsignada",clase);
        return "horario/asignacion/crear-asignacion";
    }

    @PostMapping(path = "/{clase}/asignacion/crear")
    public String procesarAsignacion(@Valid Asignacion asignacion, BindingResult bindingResult, SessionStatus sessionStatus,
                                     @ModelAttribute("estudiantes") List<Estudiante> estudiantes,
                                     @ModelAttribute("claseAsignada") Clase clase,
                                     @RequestParam(name = "asignados", required = false) List<Long> asignadosId,
                                     RedirectAttributes model){

        if(asignadosId == null || asignadosId.size() == 0){

            String errorMsg = "Debe seleccionar por lo menos un estudiante";
            bindingResult.addError(
                    new ObjectError("asignacion.asignados", errorMsg));

            return "horario/asignacion/crear-asignacion";
        }

        if (bindingResult.hasErrors() ){

            return "horario/asignacion/crear-asignacion";
        }

        Set<Estudiante> estudiantesAsignados = estudiantes.stream().
                        filter(e -> asignadosId.contains( e.getId())).
                        collect(Collectors.toSet());

        asignacion.setAsignados(estudiantesAsignados);
        asignacion.setClase(clase);

        asignacion = asignacionRepository.save(asignacion);

        model.addFlashAttribute(asignacion);

        sessionStatus.setComplete();
        return "redirect:/asignacion/"+asignacion.getId();
    }

    @PostMapping(path = "/{claseId}/asignacion/eliminar", params = "asignacionId")
    public String eliminarAsignacion(@PathVariable Long claseId, @RequestParam Long asignacionId, Model model){
        Clase clase = claseRepository.findAndFetchAsignaciones(claseId);

        boolean wasRemoved = clase.getAsignaciones().removeIf(a -> a.getId().equals(asignacionId));

        claseRepository.save(clase);

        model.addAttribute("wasRemoved",wasRemoved);
        model.addAttribute(clase);
        return "redirect:/clase/"+clase.getId()+"/asignacion/all";
    }

    @GetMapping(path = "/calificaciones/{curso}")
    public String getReporteClases(@PathVariable Curso curso, @AuthenticationPrincipal Estudiante estudiante, Model model){

        List<Clase> clases = claseRepository.findByCursoAndEstudianteFetchAsignaciones(curso, estudiante);

        Map<Clase, Integer> porcentajePorClase = this.groupPorcentajePorClase(clases, estudiante);

        model.addAttribute(curso);
        model.addAttribute(estudiante);
        model.addAttribute("porcentajes", porcentajePorClase);

        return "horario/reporte-clases";
    }

    @GetMapping(path = "/calificaciones/{curso}", params = "estudianteId")
    public String reporteClases(@PathVariable Curso curso,
                                  @RequestParam Long estudianteId, Model model){

        Estudiante estudiante = estudianteRepository.findById(estudianteId);
        List<Clase> clases = claseRepository.findByCursoAndEstudianteFetchAsignaciones(curso, estudiante);


        Map<Clase, Integer> porcentajePorClase = groupPorcentajePorClase(clases, estudiante);

        model.addAttribute(curso);
        model.addAttribute(estudiante);
        model.addAttribute("porcentajes", porcentajePorClase);

        return "horario/reporte-clases";
    }


    private Map<Clase, Integer> groupPorcentajePorClase(List<Clase> clases, Estudiante estudiante){

        clases.stream().flatMap(c -> c.getAsignaciones().stream()).
                forEach(a -> a.getCalificaciones().
                        removeIf(c -> !c.getEstudiante().equals(estudiante)));

        Map<Clase, Integer> porcentajePorClase = clases.stream().
                collect(Collectors.toMap(Function.identity(), c -> {
                    int total = c.getAsignaciones().stream().mapToInt(Asignacion::getValor).sum();

                    int acumulado = c.getAsignaciones().stream().
                            flatMap(a -> a.getCalificaciones().stream()).mapToInt(Calificacion::getPuntuacion).sum();

                    return (acumulado * 100) / total;
                }));

        return porcentajePorClase;
    }

}
