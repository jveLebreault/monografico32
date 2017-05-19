package ug.monografico32.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.dao.PeriodoRepository;
import ug.monografico32.model.Periodo;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Jose Elias on 15/05/2017.
 */

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
    public  String getAllPeriodos(Model model){
        List<Periodo> periodos = periodoRepository.findAll();
        model.addAttribute("periodos", periodos);

        return "periodo/ver-todos";
    }
}
