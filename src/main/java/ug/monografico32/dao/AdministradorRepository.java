package ug.monografico32.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.monografico32.model.Administrador;

public interface AdministradorRepository  extends JpaRepository<Administrador, Long>{
}
