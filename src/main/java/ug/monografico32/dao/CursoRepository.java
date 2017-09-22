/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.dao;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Docente;

public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso>{

    public Curso findById(Long id);

    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.horario h " +
                                 "LEFT JOIN FETCH h.clases cs " +
                                 "LEFT JOIN FETCH cs.sesiones "+
                                 "WHERE c.id = ?1")
    public Curso findByIdAndFetchHorario(Long id);


    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.estudiantes WHERE c.id = ?1")
    public Curso findByIdAndFetchEstudiantes(Long id);


    @Query("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.horario h " +
            "LEFT JOIN FETCH h.clases cs " +
            "LEFT JOIN FETCH cs.sesiones "+
            "WHERE ?1 MEMBER OF h.clases")
    Curso findCursoContainingClase(Clase clase);
    
    Stream<Curso> findByDocenteEncargadoId(Long docenteId);

    List<Curso> findByDocenteEncargadoIdAndPeriodoId(Long docenteEncargadoId, Long periodoId);
    
}
