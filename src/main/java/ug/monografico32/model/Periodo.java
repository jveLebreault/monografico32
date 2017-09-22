package ug.monografico32.model;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@FechaInicioFinalValida
public class Periodo implements Serializable{

    @Id @GeneratedValue
    private Long id;

    @Future
    @NotNull
    @Temporal( TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @Future
    @NotNull
    @Temporal( TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinal;

    @OneToMany( orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "periodo")
    private Set<Curso> cursos;

    public Periodo(Date fechaInicio, Date fechaFinal) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public Periodo(){}

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, fechaFinal);
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof Periodo) )
            return false;

        if ( obj == this)
            return true;

        Periodo other = (Periodo) obj;

        return this.fechaInicio.equals(other.getFechaInicio()) &&
                this.fechaFinal.equals(other.getFechaFinal());

    }
}
