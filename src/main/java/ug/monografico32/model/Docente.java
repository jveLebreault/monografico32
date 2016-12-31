package ug.monografico32.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@Entity
public class Docente extends Persona{

    @OneToOne(mappedBy = "docenteEncargado")
    private Curso cursoEncargado;

    public Docente(){
        super();
    }
    
    public Docente(String nombres, String apellidos){
        super(nombres, apellidos);
    }

    public void setCursoEncargado(Curso curso){
        this.cursoEncargado = curso;
    }

    public Curso getCursoEncargado(){
        return cursoEncargado;
    }
}
