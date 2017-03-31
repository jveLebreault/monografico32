package ug.monografico32.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ug.monografico32.dao.AsignaturaRepository;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Asignatura;
import ug.monografico32.model.Docente;

import java.util.List;

/**
 * Created by Jose Elias on 15/03/2017.
 */
@Service("horarioService")
public class HorarioService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Asignatura> getAllAsignaturas(){
        return asignaturaRepository.findAll();
    }

    public List<Docente> getAllDocentes(){
        return docenteRepository.findAll();
    }
}
