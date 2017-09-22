package ug.monografico32.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ug.monografico32.model.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Long>{

    public Docente findById(Long id);
}
