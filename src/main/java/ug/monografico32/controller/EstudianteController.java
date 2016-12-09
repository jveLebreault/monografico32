package ug.monografico32.controller;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ug.monografico32.model.Estudiante;

import javax.validation.Valid;

/**
 * Created by Jose Elias on 23/11/2016.
 */
/*@Controller
@RequestMapping( path = "/estudiante")
public class EstudianteController {

    @Autowired
    private AmazonS3 s3Client;

    public EstudianteController(AmazonS3 s3Client){
        this.s3Client = s3Client;
    }

    @GetMapping( path = "/agregar")
    public String enrolarEstudiante(Model model){

        model.addAttribute(new Estudiante());
        return "estudiante/agregar-estudiante";
    }

    @PostMapping( path = "/agregar")
    public String procesarEnrolar(@Valid Estudiante estudiante, Errors errors){
        //TODO: Agregar estudiante a repositorio, subir archivos a nube con base en la clave generada por el repositorio,
        //TODO: redireccionar a vista para agregar tutores
        if (errors.hasErrors()){
            return "estudiante/agregar-estudiante";
        }
        return "redirect:/home";
    }
}*/
