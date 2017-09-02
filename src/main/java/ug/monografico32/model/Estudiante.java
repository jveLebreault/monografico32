package ug.monografico32.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Valid;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@Entity
public class Estudiante extends Usuario implements Serializable{

    @NotNull
    private boolean transferido;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tutor> tutores;
    {tutores = new HashSet<>();}

    @ManyToMany( mappedBy = "estudiantes")
    private Set<Curso> cursos;
    {cursos = new HashSet<>();}

    
    public Estudiante(){
        super();
        this.addAuthority(Roles.ESTUDIANTE);
    }

    public Estudiante(String nombres, String apellidos, AmazonS3Document foto,
                      AmazonS3Document certificadoMedico, AmazonS3Document actaNacimiento){
        super();
        this.setNombres(nombres);
        this.setApellidos(apellidos);
        this.transferido = false;
        this.getDocuments().addAll( Arrays.asList(foto, certificadoMedico, actaNacimiento) );
    }

    public Estudiante(String nombres, String apellidos, AmazonS3Document foto,
                      AmazonS3Document certificadoMedico, AmazonS3Document actaNacimiento,
                      AmazonS3Document recordNotaTransferido,
                      AmazonS3Document certificadoConductaTransferido){

        this(nombres, apellidos, foto, certificadoMedico, actaNacimiento);
        this.transferido = true;
        this.getDocuments().addAll( Arrays.asList(recordNotaTransferido,
                            certificadoConductaTransferido) );
    }
    
    public void setTutores(Set<Tutor> tutores){
        this.tutores = tutores;
    }
    
    public Set<Tutor> getTutores(){
        return tutores;
    }
    
    public boolean agregarTutor(List<Tutor> tutores){
        return tutores.addAll(tutores);
    }
    
    public boolean agregarTutor(Tutor tutor){
        return tutores.add(tutor);
    }
    
    public boolean eliminarTutor(final Tutor tutor){
        return tutores.removeIf( (Tutor t)-> 
                                t.getId().equals(tutor.getId() ) );
    }

    public boolean isTransferido(){
        return transferido;
    }

    public void setTransferido(boolean transferido){
        this.transferido = transferido;
    }

    public void setCursos(Set<Curso> cursos){
        this.cursos = cursos;
    }

    public Set<Curso> getCursos(){
        return cursos;
    }

    public boolean agregarCurso(Curso curso){
        return cursos.add(curso);
    }

    public boolean eliminarCurso(final Curso curso){
        return cursos.removeIf( (Curso c)->
                                c.getId().equals( curso.getId() ) );
    }



    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof Estudiante) )
            return false;

        Estudiante other = (Estudiante) obj;

        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
