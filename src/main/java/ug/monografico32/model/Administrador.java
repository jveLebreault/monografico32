package ug.monografico32.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Administrador extends Usuario{

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 13)
    private String numeroCedula;

    public Administrador(){
        super();
        this.addAuthority(Roles.ADMINISTRADOR);
        this.setEstado(Estado.ACTIVO);
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }
}
