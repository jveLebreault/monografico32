package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Estudiante;

import java.util.List;

/**
 * Created by Jose Elias on 07/12/2016.
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
    
    Estudiante findById(Long id);

    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.cursos WHERE e.id = ?1")
    Estudiante findByIdAndFetchCursos(Long id);

    @Query("SELECT e FROM Estudiante e WHERE ?1 NOT MEMBER OF e.cursos ")
    List<Estudiante> findEstudiantesNotInCurso( Curso curso);
}