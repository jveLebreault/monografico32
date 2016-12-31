package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.monografico32.model.Docente;

/**
 * Created by Jose Elias on 25/12/2016.
 */
public interface DocenteRepository extends JpaRepository<Docente, Long>{

    public Docente findById(Long id);
}
