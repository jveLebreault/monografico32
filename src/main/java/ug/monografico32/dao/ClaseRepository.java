package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ug.monografico32.model.Clase;

/**
 * Created by Jose Elias on 20/04/2017.
 */
public interface ClaseRepository extends JpaRepository<Clase, Long>{

    @Query("SELECT c FROM Clase c LEFT JOIN FETCH c.sesiones WHERE c.id = ?1")
    Clase findAndFetchSesiones(Long id);
}
