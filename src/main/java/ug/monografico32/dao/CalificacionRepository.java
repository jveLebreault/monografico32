package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ug.monografico32.model.Asignacion;
import ug.monografico32.model.Calificacion;
import ug.monografico32.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long>{

    Calificacion findByAsignacionAndEstudiante(Asignacion asignacion, Estudiante estudiante);

    Calificacion findByAsignacionIdAndEstudianteId(Long asignacionId, Long estudianteId);

    Optional<Calificacion> getByAsignacionIdAndEstudianteId(Long asignacionId, Long estudianteId);

    @Modifying
    @Query("UPDATE Calificacion c SET c.puntuacion = ?1 WHERE c.id = ?2")
    int updateCalificacion(int puntuacion, Long id);

    List<Calificacion> findByAsignacion(Asignacion asignacion);
}
