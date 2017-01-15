package ug.monografico32.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Docente;

import javax.jws.WebParam;
import javax.validation.Valid;

/**
 * Created by Jose Elias on 20/12/2016.
 */
@Controller
@RequestMapping( path = "/docente")
public class DocenteController {

    @Autowired
    private DocenteRepository repository;

    public DocenteController(DocenteRepository repository){
        this.repository = repository;
    }

    @GetMapping(path = "/agregar")
    public String agregarDocente(Model model){
        model.addAttribute( new Docente() );
        return "docente/agregar-docente";
    }

    @PostMapping(path = "/agregar")
    public String procesarDocente(@Valid Docente docente, BindingResult result, Model model){

        if( result.hasErrors() )
            return "docente/agregar-docente";

        docente = repository.save(docente);
        //model.addAttribute(docente);
        return "docente/docente-detalle";

    }

    @GetMapping( path = "/{id}")
    public String verDocente(@PathVariable Long id, Model model){
        model.addAttribute( repository.findById(id) );
        return "docente/docente-detalle";
    }
    
    @GetMapping( path="/all")
    public String verTodos(Model model){
        List<Docente> docentes = repository.findAll();
        /*if( docentes == null || docentes.isEmpty())
            return"";*/
        model.addAttribute("docentes", docentes);
        return "docente/todos";
    }

}
