package ug.monografico32.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.AmazonS3Document;
import ug.monografico32.model.Docente;
import ug.monografico32.model.DocumentType;
import ug.monografico32.model.aws.AWSFileUploader;

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

    @Autowired
    private AWSFileUploader fileUploader;

    public DocenteController(DocenteRepository repository, AWSFileUploader fileUploader){
        this.repository = repository;
        this.fileUploader = fileUploader;
    }

    @GetMapping(path = "/agregar")
    public String agregarDocente(Model model){
        model.addAttribute( new Docente() );
        return "docente/agregar-docente";
    }

    @PostMapping(path = "/agregar")
    public String procesarDocente(@Valid Docente docente, BindingResult result, RedirectAttributes model,
                                  @RequestParam("docente-cedula") MultipartFile file) throws IOException{

        if( result.hasErrors() ){
            return "docente/agregar-docente";
        }

        docente = repository.save(docente);

        AmazonS3Document cedulaFile = fileUploader.uploadFile(file.getInputStream(), docente, DocumentType.CEDULA);

        docente.agregarDocumento(cedulaFile);

        docente = repository.saveAndFlush(docente);

        model.addFlashAttribute("usuario", docente);

        return "redirect:agregar/confirmar";
    }

    @GetMapping(path = "/agregar/confirmar")
    public String confirmarCreacion(){

        return "usuario/confirmacion-creacion-usuario";
    }

    @GetMapping( path = "/{id}")
    public String verDocente(@PathVariable Long id, Model model){
        model.addAttribute( repository.findById(id) );
        return "docente/docente-detalle";
    }
    
    @GetMapping( path="/all")
    public String verTodos(Model model, Pageable pageable){
        Page<Docente> docentes = repository.findAll(pageable);

        model.addAttribute(new Docente());
        model.addAttribute("docentes", docentes);
        return "docente/todos";
    }

    @GetMapping( path="/all", params = {"nombres","apellidos", "numeroCedula"})
    public String buscarDocente(Docente docente, Model model, Pageable pageable){

        ExampleMatcher matcher = ExampleMatcher.matching().
                                withIgnoreCase().
                                withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Docente> docenteExample = Example.of(docente, matcher);

        Page<Docente> docentes = repository.findAll(docenteExample, pageable);

        model.addAttribute("docentes", docentes);
        return "docente/todos";
    }

}
