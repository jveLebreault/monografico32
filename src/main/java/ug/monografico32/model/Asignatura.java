package ug.monografico32.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Jose Elias on 28/01/2017.
 */
@Entity
public class Asignatura implements Serializable {

    @Id @GeneratedValue
    private Long id;
    
    @NotNull
    @Size(min = 10, max = 60)
    private String nombre;
    
    @NotNull
    @Size(min = 3, max = 10)
    @Column(unique = true)
    private String clave;

    public Asignatura(){}

    public Asignatura(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
}
