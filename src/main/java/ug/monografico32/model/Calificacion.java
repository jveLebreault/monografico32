package ug.monografico32.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"asignacion_id","estudiante_id"})
        })
public class Calificacion implements Serializable{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Asignacion asignacion;

    @OneToOne
    private Estudiante estudiante;

    @NotNull
    private int puntuacion;

    public Calificacion(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {

        this.puntuacion = puntuacion;
    }

    public boolean calificar(int puntuacion){

        if (puntuacion > asignacion.getValor()){
            return false;
        }

        setPuntuacion(puntuacion);
        return true;
    }

    public boolean asignar(Estudiante estudiante){

        if(asignacion.getAsignados().contains(estudiante)) {
            setEstudiante(estudiante);
            return true;
        }

        return false;
    }
}


