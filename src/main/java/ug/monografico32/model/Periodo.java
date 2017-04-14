package ug.monografico32.model;

import org.springframework.format.annotation.DateTimeFormat;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Jose Elias on 13/04/2017.
 */
@FechaInicioFinalValida
public class Periodo {

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
}
