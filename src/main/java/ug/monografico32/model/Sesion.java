package ug.monografico32.model;

import org.hibernate.annotations.Parent;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class Sesion implements Serializable {

    @Parent
    private Clase clase;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;

    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date horaInicio;

    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date horaFinal;

    public Sesion() {}
    
    public Sesion(DayOfWeek dia, Date inicio, Date fin){
        this.dia = dia;
        this.horaInicio = inicio;
        this.horaFinal = fin;
    }

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

    public Clase getClase(){
        return this.clase;
    }

    public void setClase(Clase clase){
        this.clase = clase;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Sesion))
            return false;
        
        if (this == obj)
            return true;

        final Sesion other = (Sesion) obj;
        return checkForCollision(this, other);
    }
    
    /**
     * Verifica is dos sesiones colisionan
     * @param s1
     * @param s2
     * @return 
     */
    public static boolean checkForCollision(Sesion s1, Sesion s2){
        if( s1.getDia().equals(s2.getDia()) ){
            //Checks if s1 and s2 have the same starting or finishing time
            boolean c1 = s1.getHoraInicio().equals(s2.getHoraInicio()) || 
                         s1.getHoraFinal().equals(s2.getHoraFinal());
            //Checks if s1 is contained in s2
            boolean c2 = s1.getHoraInicio().after(s2.getHoraInicio()) && 
                         s1.getHoraFinal().before(s2.getHoraFinal());
            //Checks if s2 is contained in s1
            boolean c3 = s2.getHoraInicio().after(s1.getHoraInicio()) &&
                         s2.getHoraFinal().before(s1.getHoraFinal());
            //Checks if s1 starts before s2 starts and if s1 ends after s2 finishes
            boolean c4 = s1.getHoraInicio().before(s2.getHoraInicio()) && 
                         s1.getHoraFinal().after(s2.getHoraInicio());
            boolean c5 = s2.getHoraInicio().before(s1.getHoraInicio()) && 
                         s2.getHoraFinal().after(s1.getHoraInicio());

            return c1 || c2 || c3 || c4 || c5;
        }
        return false;
    }
    
}
