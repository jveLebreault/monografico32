package ug.monografico32.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@Entity
public class Docente extends PersonaLegal implements Serializable{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docenteEncargado")
    private List<Curso> cursosEncargado;
    {cursosEncargado = new ArrayList<>();}

    public Docente(){
        super();
    }
    
    public Docente(String nombres, String apellidos, String cedula){
        super(nombres, apellidos, cedula);
    }

    public void setCursoEncargado(List<Curso> curso){
        this.cursosEncargado = curso;
    }

    public List<Curso> getCursoEncargado(){
        return cursosEncargado;
    }
}
