package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.monografico32.model.Estudiante;

/**
 * Created by Jose Elias on 07/12/2016.
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
    
    Estudiante findById(Long id);
}