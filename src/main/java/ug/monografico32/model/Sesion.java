package ug.monografico32.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * Created by Jose Elias on 01/02/2017.
 */
public class Sesion {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;

    private Date horaInicio;

    private Date horaFinal;

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

}
