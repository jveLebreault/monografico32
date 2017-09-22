package ug.monografico32.controller;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
import ug.monografico32.model.Curso;
import ug.monografico32.model.Estudiante;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @GetMapping( path = "/{id}")
    public String showById(@PathVariable Long id, Model model){
        model.addAttribute( repository.findByIdAndFetchTutores(id) );
        return "estudiante/estudiante-detalle";
    }

    @GetMapping( path = "/{id}/cursos")
    public String verCursosEstudiante(@PathVariable Long id,Model model){
        Estudiante estudiante = repository.findByIdAndFetchCursos(id);
        List<Curso> cursos = ordenarCursos( estudiante.getCursos() );

        model.addAttribute(estudiante);
        model.addAttribute("cursos", cursos);

        return "estudiante/estudiante-cursos";
    }

    @GetMapping(path = "/all")
    public String verTodos(Model model, Pageable pageable){

        Page<Estudiante> estudiantes = repository.findAll(pageable);

        model.addAttribute(new Estudiante());
        model.addAttribute("estudiantes", estudiantes);
        return "estudiante/ver-todos";
    }

    @GetMapping(path = "/all", params = {"nombres", "apellidos"})
    public String buscar(Estudiante estudiante, Model model, Pageable pageable){

        ExampleMatcher matcher = ExampleMatcher.matching().
                                    withIgnorePaths("transferido").
                                    withIgnoreCase().
                                    withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Estudiante> estudianteExample = Example.of(estudiante, matcher);

        Page<Estudiante> estudiantes = repository.findAll(estudianteExample, pageable);

        model.addAttribute("estudiantes", estudiantes);
        return "estudiante/ver-todos";
    }



    private List<Curso> ordenarCursos(Set<Curso> cursos){
        List<Curso> cursosOrdenados = new ArrayList<>(cursos);

        cursosOrdenados.sort( (c1, c2) -> {
            Date fechaInicioC1 = c1.getPeriodo().getFechaInicio();
            Date fechaInicioC2 = c2.getPeriodo().getFechaInicio();

            return fechaInicioC1.compareTo(fechaInicioC2);
        });

        return cursosOrdenados;
    }

}
