package ug.monografico32.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@Entity
public class Docente extends Persona{

    @OneToMany(fetch = FetchType.LAZY)
    private List<Curso> cursosEncargado;

    public Docente(){
        super();
    }
    
    public Docente(String nombres, String apellidos){
        super(nombres, apellidos);
    }

    public void setCursoEncargado(List<Curso> curso){
        this.cursosEncargado = curso;
    }

    public List<Curso> getCursoEncargado(){
        return cursosEncargado;
    }
}
