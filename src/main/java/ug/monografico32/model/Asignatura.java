package ug.monografico32.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

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

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;

        if(obj == this)
            return true;

        if(obj instanceof Asignatura){
            Asignatura param = (Asignatura) obj;

            return (this.getClave().equals(param.getClave()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getClave().hashCode();
    }
}
