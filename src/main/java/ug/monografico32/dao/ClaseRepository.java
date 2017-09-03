package ug.monografico32.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Docente;
import ug.monografico32.model.Periodo;

/**
 * Created by Jose Elias on 20/04/2017.
 */
public interface ClaseRepository extends JpaRepository<Clase, Long>{

    @Query("SELECT c FROM Clase c LEFT JOIN FETCH c.sesiones WHERE c.id = ?1")
    Clase findAndFetchSesiones(Long id);

    @Query("SELECT c FROM Clase c LEFT JOIN FETCH c.sesiones WHERE c.instructor.id = ?1")
    Stream<Clase> findByInstructorId(Long id);

    @Query("SELECT DISTINCT c FROM Clase c LEFT JOIN FETCH c.sesiones " +
            "WHERE c.instructor = ?1 AND c.horario.curso.periodo = ?2")
    List<Clase> findListByDocenteAndPeriodo(Docente id, Periodo periodo);

    @Query("SELECT c FROM Clase c LEFT JOIN FETCH c.sesiones " +
            "WHERE c.instructor.id = ?1 AND c.horario.curso.periodo.id = ?2")
    Stream<Clase> findByInstructorIdAndPeriodoId(Long instructorId, Long periodoId);
}
