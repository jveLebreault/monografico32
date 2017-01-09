package ug.monografico32.controller;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.dao.EstudianteRepository;
import ug.monografico32.model.Estudiante;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Jose Elias on 23/11/2016.
 */
@Controller
@RequestMapping( path = "/estudiante")
public class EstudianteController {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EstudianteRepository repository;

    public EstudianteController(AmazonS3 s3Client, EstudianteRepository repository){
        this.s3Client = s3Client;
        this.repository = repository;
    }

    @GetMapping( path = "/info/{id}")
    public String showById(@PathVariable Long id, Model model){
        model.addAttribute( repository.findById(id) );
        return "estudiante/estudiante-detalle";
    }

    /*@GetMapping(path = "/detalle")
    public String verDetalles(Estudiante estudiante, Model model){
        estudiante = repository.save(estudiante);
        model.addAttribute(estudiante);

        return "estudiante/estudiante-detalle";
    }*/


}
