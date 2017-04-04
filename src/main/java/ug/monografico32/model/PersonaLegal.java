package ug.monografico32.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Jose Elias on 03/03/2017.
 */
@MappedSuperclass
abstract public class PersonaLegal extends Persona{

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 13)
    private String numeroCedula;

    public PersonaLegal(){}

    public PersonaLegal(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public PersonaLegal(String nombres, String apellidos, String numeroCedula) {
        super(nombres, apellidos);
        this.numeroCedula = numeroCedula;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String cedula) {
        this.numeroCedula = cedula;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj == this)
            return true;

        if(obj instanceof PersonaLegal){
            PersonaLegal pl = (PersonaLegal) obj;
            return (this.getNumeroCedula().equals( pl.getNumeroCedula() ));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCedula);
    }
}
