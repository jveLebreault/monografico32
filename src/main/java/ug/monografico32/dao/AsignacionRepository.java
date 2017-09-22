package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ug.monografico32.model.Asignacion;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Estudiante;

import java.util.List;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long>{

    List<Asignacion> findByClase(Clase clase);

    List<Asignacion> findByClaseAndAsignadosContains(Clase clase, Estudiante estudiante);

    @Query("SELECT DISTINCT a FROM Asignacion a LEFT JOIN FETCH a.asignados WHERE a.id = ?1")
    Asignacion findByIdAndFetchAsignados(Long id);

    @Query("SELECT DISTINCT a FROM Asignacion a LEFT JOIN FETCH a.calificaciones cf " +
            "WHERE ?1 MEMBER OF a.asignados AND a.clase.id = ?2 ")
    List<Asignacion> findByAsignadosContainsAndClaseId(Estudiante estudiante, Long claseId);
}