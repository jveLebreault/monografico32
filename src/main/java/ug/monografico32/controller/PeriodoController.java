package ug.monografico32.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ug.monografico32.dao.PeriodoRepository;
import ug.monografico32.model.Periodo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/periodo")
public class PeriodoController {

    @Autowired
    private PeriodoRepository periodoRepository;

    @GetMapping(path = "/crear")
    public String crearPeriodo(Model model){
        model.addAttribute(new Periodo());
        return "periodo/crear-periodo";
    }

    @PostMapping( path = "/crear")
    public String procesarPeriodo(@Valid Periodo periodo, BindingResult result){
        if(result.hasErrors())
            return "periodo/crear-periodo";

        periodo = periodoRepository.save(periodo);
        return "redirect:"+periodo.getId();

    }

    @GetMapping( path = "/{id}")
    public String gePeriodoById(@PathVariable Long id, Model model){
        Periodo periodo = periodoRepository.findAndFetchCursos(id);
        model.addAttribute(periodo);

        return "periodo/ver-periodo";
    }

    @GetMapping( path = "/all" )
    public  String getAllPeriodos(Model model, Pageable pageable){
        Page<Periodo> periodos = periodoRepository.findAll(pageable);

        model.addAttribute("periodos", periodos);
        return "periodo/ver-todos";
    }

    @GetMapping( path = "/all", params = "year")
    public  String buscarPeriodos(@RequestParam Integer year, Model model, Pageable pageable){

        Page<Periodo> periodos;

        if( year == null) {
            periodos = periodoRepository.findAll(pageable);
        } else {
            periodos = periodoRepository.findAllByYear(year, pageable);
        }

        model.addAttribute("periodos", periodos);
        return "periodo/ver-todos";
    }

    @GetMapping( path = "/seleccionar")
    public String seleccionarPeriodo(@RequestAttribute(required = false) String previousServletPath,
                                                                            Model model){
        model.addAttribute("periodos", periodoRepository.findAll());
        model.addAttribute(new Periodo());

        if (previousServletPath != null)
            model.addAttribute("previousServletPath", previousServletPath);

        return "periodo/seleccionar-periodo";
    }

    @PostMapping( path = "/seleccionar")
    public String procesarSeleccion(@RequestParam Periodo periodo, HttpSession session,
                                    @RequestParam(required = false) String previousServletPath){

        session.setAttribute("periodo", periodo);

        if (previousServletPath != null)
            return "redirect:"+previousServletPath;

        return "home";
    }
}
