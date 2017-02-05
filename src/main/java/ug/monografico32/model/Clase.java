package ug.monografico32.model;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jose Elias on 28/01/2017.
 */
public class Clase {

    private Asignatura asignatura;

    private Docente instructor;

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

    public boolean agregarSesion(final Sesion sesion) throws CollisionException{
        Set<Sesion> colisiones = sesiones.stream()
                            .filter(e -> Sesion.checkForCollision(sesion, e))
                            .collect(Collectors.toSet());
        
        if(colisiones.size()>0){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            StringBuilder bld = new StringBuilder();
            bld.append("Colisiones detectadas en: ");
            
            for(Sesion s: sesiones){
                bld.append(s.getDia()).append(": ").append(df.format(s.getHoraInicio()))
                   .append("-").append(df.format(s.getHoraFinal())).append(". ");
            }
            throw new CollisionException(bld.toString());
        }
        
        return sesiones.add(sesion);
    }

    public boolean removerSesion(Sesion sesion){
        return sesiones.remove(sesion);
    }
    /**
     * Package private class to express CollisonExceptions in a Clase Object
     */
    class CollisionException extends Exception {

        public CollisionException(String msg) {
            super(msg);
        }
    }
}
