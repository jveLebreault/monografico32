package ug.monografico32.model;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Created by Jose Elias on 28/01/2017.
 */
public class Clase {

    private Asignatura asignatura;

    private Docente instructor;

    private Set<Sesion> sesiones;

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
        //sesiones.stream().anyMatch()
        return sesiones.add(sesion);
    }

    public boolean removerSesion(Sesion sesion){
        return sesiones.remove(sesion);
    }
}
