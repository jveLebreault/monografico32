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
public class Estudiante extends Persona implements Serializable{

    @NotNull
    private boolean transferido;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tutor> tutores;
    {tutores = new ArrayList<>();}

    @ManyToMany( mappedBy = "estudiantes")
    private Set<Curso> cursos;
    {cursos = new HashSet<>();}
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<AmazonS3Document> documents;
    {documents = new HashSet<>();}
    
    public Estudiante(){
        super();
    }
    
    public Estudiante(String nombres, String apellidos, AmazonS3Document foto,
                      AmazonS3Document certificadoMedico, AmazonS3Document actaNacimiento){
        super(nombres,apellidos);
        this.transferido = false;
        documents.addAll( Arrays.asList(foto, certificadoMedico, actaNacimiento) );
    }
    
    public Estudiante(String nombres, String apellidos, AmazonS3Document foto,
                      AmazonS3Document certificadoMedico, AmazonS3Document actaNacimiento,
                      AmazonS3Document recordNotaTransferido,
                      AmazonS3Document certificadoConductaTransferido){
        
        this(nombres, apellidos, foto, certificadoMedico, actaNacimiento);
        this.transferido = true;
        documents.addAll( Arrays.asList(recordNotaTransferido,
                            certificadoConductaTransferido) );
    }
    
    public void setTutores(List<Tutor> tutores){
        this.tutores = tutores;
    }
    
    public List<Tutor> getTutores(){
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

    public void setDocuments(Set<AmazonS3Document> docs){
        this.documents = docs;
    }

    public Set<AmazonS3Document> getDocuments(){
        return documents;
    }

    public boolean agregarDocumentos(AmazonS3Document... docs){
        return documents.addAll( Arrays.asList(docs) );
    }

    public boolean agregarDocumento(AmazonS3Document doc){
        return documents.add(doc);
    }

    public boolean eliminarDocumento(final AmazonS3Document doc){
        return documents.removeIf( (d)->
                                    d.equals(doc) );
    }

    @Override
    public boolean equals(Object obj) {
        if( !(obj instanceof Estudiante) )
            return false;

        Estudiante other = (Estudiante) obj;

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}
