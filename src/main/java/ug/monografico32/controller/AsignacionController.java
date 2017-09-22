package ug.monografico32.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ug.monografico32.dao.AsignacionRepository;
import ug.monografico32.dao.CalificacionRepository;
import ug.monografico32.dao.ClaseRepository;
import ug.monografico32.dao.EstudianteRepository;
import ug.monografico32.model.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes(value = {"estudianteACalificar","asignacionACalificar"})
@RequestMapping(path = "/asignacion" )
public class AsignacionController {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("asignacion", "estudiante");
    }

    @GetMapping(path = "/{id}")
    public String verAsignacion(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario, Model model){

        if (model.containsAttribute("asignacion"))
            return "horario/asignacion/asignacion-detalle";

        Asignacion asignacion = asignacionRepository.findByIdAndFetchAsignados(id);
        model.addAttribute(asignacion);
        model.addAttribute("usuario", usuario);
        return "horario/asignacion/asignacion-detalle";
    }

    @GetMapping(path = "/calificar", params = {"asignacionId","estudianteId"})
    public String calificar(@RequestParam Long asignacionId, @RequestParam Long estudianteId, Model model){

        Asignacion asignacion = asignacionRepository.findByIdAndFetchAsignados(asignacionId);
        Estudiante estudiante = asignacion.getAsignados().
                stream().filter(e -> e.getId().equals(estudianteId)).findFirst().get();

        Calificacion calificacion = calificacionRepository.getByAsignacionIdAndEstudianteId(asignacionId, estudianteId)
                                    .orElse( new Calificacion());

        model.addAttribute("asignacionACalificar", asignacion);
        model.addAttribute("estudianteACalificar", estudiante);
        model.addAttribute(calificacion);

        return "horario/asignacion/calificar-asignacion";
    }

    @Transactional
    @PostMapping(path = "/calificar")
    public String procesarCalificacion(@Valid Calificacion calificacion, BindingResult errors,
                                       @ModelAttribute("asignacionACalificar") Asignacion asignacion,
                                       @ModelAttribute("estudianteACalificar") Estudiante estudiante,
                                       SessionStatus sessionStatus, RedirectAttributes model){

        if (calificacion.getPuntuacion() > asignacion.getValor()){
            errors.addError(new ObjectError("calificacion.puntuacion", "Puntuacion no puede ser mayor al valor de la calificacion"));
            return "horario/asignacion/calificar-asignacion";
        }

        if (errors.hasErrors())
            return "horario/asignacion/calificar-asignacion";

        calificacion.setAsignacion(asignacion);
        calificacion.setEstudiante(estudiante);

        if( calificacion.getId() == null ){
            calificacion = calificacionRepository.save(calificacion);
        }
        else{
            this.updateCalificacion(calificacion);
        }

        model.addFlashAttribute(calificacion);

        sessionStatus.setComplete();
        return "redirect:"+asignacion.getId()+"/calificacion?estudianteId="+estudiante.getId();
    }

    private int updateCalificacion(Calificacion calificacion){

        return calificacionRepository.updateCalificacion(calificacion.getPuntuacion(), calificacion.getId());
    }

    @GetMapping(path = "/{asignacionId}/calificacion", params = "estudianteId")
    public String getCalificacion(@PathVariable Long asignacionId, @RequestParam Long estudianteId, Model model){

        if( model.containsAttribute("calificacion"))
            return "horario/asignacion/calificacion-detalle";

        Calificacion calificacion = calificacionRepository.findByAsignacionIdAndEstudianteId(asignacionId, estudianteId);
        model.addAttribute("calificacion", calificacion);
        return "horario/asignacion/calificacion-detalle";
    }

    @GetMapping(path = "/{claseId}/calificaciones")
    public String getReporteDeAsignaciones(@AuthenticationPrincipal Estudiante estudiante,
                                           @PathVariable Long claseId, Model model){

        List<Asignacion> asignaciones = asignacionRepository.findByAsignadosContainsAndClaseId(estudiante, claseId);

        for(Asignacion asignacion : asignaciones){
            asignacion.getCalificaciones().removeIf( c -> !c.getEstudiante().equals(estudiante));
        }

        int total = asignaciones.stream().map(Asignacion::getValor).reduce(0, Integer::sum);
        int acumulado = asignaciones.stream().
                flatMap(a -> a.getCalificaciones().stream()).
                map(Calificacion::getPuntuacion).reduce(0, Integer::sum);

        int porcentaje = (acumulado * 100)/ total;

        model.addAttribute("asignaciones", asignaciones);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("total", total);
        model.addAttribute("acumulado", acumulado);
        model.addAttribute("porcentaje", porcentaje);
        return "horario/asignacion/calificaciones-estudiante";
    }

    @GetMapping(path = "/{claseId}/calificaciones/{estudianteId}")
    public String getReporteDeAsignaciones(@PathVariable Long claseId, @PathVariable Long estudianteId, Model model){
        Estudiante estudiante = estudianteRepository.findById(estudianteId);

        return getReporteDeAsignaciones(estudiante, claseId, model);
    }

}
