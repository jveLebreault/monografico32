package ug.monografico32.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@Entity
public class Docente extends Usuario implements Serializable{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docenteEncargado")
    private List<Curso> cursosEncargado;
    {cursosEncargado = new ArrayList<>();}

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 13)
    private String numeroCedula;

    public Docente(){
        super();
        this.addAuthority(Roles.DOCENTE);
    }
    
    public Docente(String nombres, String apellidos, String cedula){
        super();
        this.setNombres(nombres);
        this.setApellidos(apellidos);
        this.numeroCedula = cedula;
    }

    public List<Curso> getCursosEncargado() {
        return cursosEncargado;
    }

    public void setCursosEncargado(List<Curso> cursosEncargado) {
        this.cursosEncargado = cursosEncargado;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }
}
