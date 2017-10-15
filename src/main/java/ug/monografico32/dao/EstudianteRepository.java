package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Estudiante;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long>, QueryByExampleExecutor<Estudiante>, JpaSpecificationExecutor<Estudiante> {
    
    Estudiante findById(Long id);

    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.documents " +
            "JOIN FETCH e.tutores t JOIN FETCH t.documents WHERE e.id = ?1")
    Estudiante findByIdAndFetchTutores(Long id);

    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.cursos WHERE e.id = ?1")
    Estudiante findByIdAndFetchCursos(Long id);

    List<Estudiante> findByCursosIn(Curso... cursos);
}