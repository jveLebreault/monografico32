/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.monografico32.model.Curso;

/**
 *
 * @author Administrador
 */
public interface CursoRepository extends JpaRepository<Curso, Long>{

    public Curso findById(Long id);
    
}
