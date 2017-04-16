package ug.monografico32.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Jose Elias on 28/01/2017.
 */
@Entity
public class Clase implements Serializable {

    @Id @GeneratedValue
    private Long id;
    
    @NotNull
    @OneToOne
    private Asignatura asignatura;
    
    @NotNull
    @OneToOne
    private Docente instructor;

    /**
     * sesiones is a set so it doesn't accept duplicate Sesion objects
     */
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Sesion> sesiones;
    {sesiones = new HashSet<>();}
    
    public Clase(){}
    
    public Clase(Asignatura asignatura, Docente instructor){
        this.asignatura = asignatura;
        this.instructor = instructor;
    }
    
    public Clase(Asignatura asignatura, Docente instructor, Set<Sesion> sesiones){
        this.asignatura = asignatura;
        this.instructor = instructor;
        this.sesiones = sesiones;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setInstructor(Docente instructor) {
        this.instructor = instructor;
    }

    public Docente getInstructor() {
        return instructor;
    }

    public void setSesiones(Set<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public Set<Sesion> getSesiones() {
        return sesiones;
    }

    public boolean agregarSesion(final Sesion sesion){
        return sesiones.add(sesion);
    }

    public boolean agregarSesion(final Set<Sesion> sesiones){
        return this.sesiones.addAll(sesiones);
    }

    public boolean removerSesion(Sesion sesion){
        return this.sesiones.remove(sesion);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj == this)
            return true;

        if(obj instanceof Clase){
            Clase clase = (Clase) obj;

            return ( (this.getAsignatura().equals(clase.getAsignatura())) &&
                     (this.getInstructor().equals(clase.getInstructor())) );
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(asignatura, instructor);
    }
}
