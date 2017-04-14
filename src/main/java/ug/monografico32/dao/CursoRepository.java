/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ug.monografico32.model.Curso;

/**
 *
 * @author Administrador
 */
public interface CursoRepository extends JpaRepository<Curso, Long>{

    public Curso findById(Long id);

    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.horario h " +
                                 "LEFT JOIN FETCH h.clases cs " +
                                 "LEFT JOIN FETCH cs.sesiones "+
                                 "WHERE c.id = ?1")
    public Curso findByIdAndFetchHorario(Long id);

    @Query("SELECT c FROM Curso c JOIN FETCH c.estudiantes WHERE c.id = ?1")
    public Curso findByIdAndFetchEstudiantes(Long id);
    
}
