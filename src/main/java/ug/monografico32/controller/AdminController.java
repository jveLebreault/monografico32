package ug.monografico32.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ug.monografico32.dao.AdministradorRepository;
import ug.monografico32.model.Administrador;
import ug.monografico32.model.AmazonS3Document;
import ug.monografico32.model.DocumentType;
import ug.monografico32.model.aws.AWSFileUploader;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AWSFileUploader fileUploader;

    public AdminController(AdministradorRepository administradorRepository, AWSFileUploader fileUploader){
        this.administradorRepository = administradorRepository;
        this.fileUploader = fileUploader;
    }

    @GetMapping(path = "/agregar")
    public String agregarAdmin(Model model){

        model.addAttribute(new Administrador());

        return "admin/agregar-admin";
    }

    @PostMapping(path = "/agregar")
    public String procesarAdmin(@Valid Administrador administrador, BindingResult result, RedirectAttributes model,
                                @RequestParam("admin-cedula") MultipartFile file) throws IOException {

        if(result.hasErrors()){
            return "admin/agregar-admin";
        }

        administrador = administradorRepository.save(administrador);

        AmazonS3Document cedulaFile = fileUploader.uploadFile(file.getInputStream(), administrador, DocumentType.CEDULA);

        administrador.agregarDocumento(cedulaFile);

        administrador = administradorRepository.saveAndFlush(administrador);

        model.addFlashAttribute("usuario", administrador);

        return "redirect:agregar/confirmar";
    }

    @GetMapping(path = "agregar/confirmar")
    public String confirmarCreacion(){

        return "usuario/confirmacion-creacion-usuario";
    }
}
