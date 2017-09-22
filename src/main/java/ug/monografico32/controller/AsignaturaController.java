package ug.monografico32.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.dao.AsignaturaRepository;
import ug.monografico32.model.Asignatura;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/asignatura")
public class AsignaturaController {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @GetMapping("/agregar")
    public String agregarAsignatura(Model model){
        model.addAttribute( new Asignatura());
        return "asignatura/agregar-asignatura";
    }

    @PostMapping(path = "/agregar")
    public String procesarAsignatura(@Valid Asignatura asignatura, BindingResult result){
        if (result.hasErrors()){
            return "asignatura/agregar-asignatura";
        }
        asignatura = asignaturaRepository.save(asignatura);
        return "redirect:"+asignatura.getId();
    }

    @GetMapping(path = "/{id}")
    public String verDetalle(@PathVariable Long id, Model model){
        Asignatura asignatura = asignaturaRepository.findOne(id);
        model.addAttribute( asignatura );

        return "asignatura/detalle";
    }

    @GetMapping(path = "/all")
    public String verTodos(Model model, Pageable pageable){
        Page<Asignatura> asignaturas = asignaturaRepository.findAll(pageable);

        model.addAttribute(new Asignatura());
        model.addAttribute("asignaturas", asignaturas);
        return "asignatura/todas";
    }

    @GetMapping(path = "/all", params = {"nombre","clave"})
    public String buscarAsigntura(Asignatura asignatura, Model model, Pageable pageable){

        ExampleMatcher matcher = ExampleMatcher.matching().
                                                withIgnoreCase().
                                                withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Asignatura> asignaturaExample = Example.of(asignatura, matcher);

        Page<Asignatura> asignaturas = asignaturaRepository.findAll(asignaturaExample, pageable);

        model.addAttribute("asignaturas", asignaturas);
        return "asignatura/todas";
    }
}
