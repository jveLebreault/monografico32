package ug.monografico32.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ug.monografico32.model.Periodo;

import java.sql.Date;

/**
 * Created by Jose Elias on 15/05/2017.
 */
public interface PeriodoRepository extends JpaRepository<Periodo, Long>{

    @Query("SELECT p FROM Periodo p LEFT JOIN FETCH p.cursos WHERE p.id = ?1")
    Periodo findAndFetchCursos(Long id);

    @Query("SELECT DISTINCT p FROM Periodo p WHERE YEAR (p.fechaInicio) = ?1 OR YEAR (p.fechaFinal) = ?1")
    Page<Periodo> findAllByYear(int year, Pageable pageable);
}
