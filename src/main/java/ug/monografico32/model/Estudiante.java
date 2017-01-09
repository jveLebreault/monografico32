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
//TODO: A custom validation to validate the existence of documents from transfered students
@Entity
public class Estudiante extends Persona implements Serializable{

    /*private CloudDocument foto;

    private CloudDocument certificadoMedico;

    private CloudDocument actaNacimiento;*/

    @NotNull
    private boolean transferido;
    /*private CloudDocument recordNotaTransferido;
    private CloudDocument certificadoBuenaConductaTransferido;*/

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tutor> tutores;
    {tutores = new ArrayList<>();}

    @ManyToMany( mappedBy = "estudiantes")
    private List<Curso> cursos;
    {cursos = new ArrayList<>();}
    
    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<DocumentType, CloudDocument> documents;
    {documents = new EnumMap(DocumentType.class);}*/
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CloudDocument> documents;
    {documents = new HashSet<>();}
    
    public Estudiante(){
        super();
    }
    
    public Estudiante(String nombres, String apellidos, CloudDocument foto, 
                CloudDocument certificadoMedico, CloudDocument actaNacimiento){
        super(nombres,apellidos);
        /*this.foto = foto;
        this.certificadoMedico = certificadoMedico;
        this.actaNacimiento = actaNacimiento;*/
        /*tutores = new ArrayList<>();
        cursos = new ArrayList<>();*/
        this.transferido = false;
        documents.addAll( Arrays.asList(foto, certificadoMedico, actaNacimiento) );
    }
    
    public Estudiante(String nombres, String apellidos, CloudDocument foto, 
                CloudDocument certificadoMedico, CloudDocument actaNacimiento, 
                CloudDocument recordNotaTransferido, 
                CloudDocument certificadoConductaTransferido){
        
        this(nombres, apellidos, foto, certificadoMedico, actaNacimiento);
        this.transferido = true;
        documents.addAll( Arrays.asList(recordNotaTransferido,
                            certificadoConductaTransferido) );
        /*this.recordNotaTransferido = recordNotaTransferido;
        this.certificadoBuenaConductaTransferido = certificadoConductaTransferido;*/
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

    /*public void setFoto(CloudDocument foto){
        this.foto = foto;
    }

    public CloudDocument getFoto(){
        return foto;
    }

    public void setCertificadoMedico(CloudDocument certificadoMedico){
        this.certificadoMedico = certificadoMedico;
    }

    public CloudDocument getCertificadoMedico(){
        return certificadoMedico;
    }

    public void setActaNacimiento(CloudDocument actaNacimiento){
        this.actaNacimiento = actaNacimiento;
    }

    public CloudDocument getActaNacimiento(){
        return actaNacimiento;
    }

    public void setRecordNotaTransferido(CloudDocument recordNotaTransferido){
        this.recordNotaTransferido = recordNotaTransferido;
    }

    public CloudDocument getRecordNotaTransferido(){
        return recordNotaTransferido;
    }

    public void setCertificadoBuenaConductaTransferido(CloudDocument certificadoBuenaConductaTransferido){
        this.certificadoBuenaConductaTransferido = certificadoBuenaConductaTransferido;
    }

    public CloudDocument getCertificadoBuenaConductaTransferido(){
        return certificadoBuenaConductaTransferido;
    }

    public void setCursos(List<Curso> cursos){
        this.cursos = cursos;
    }*/
    public void setCursos(List<Curso> cursos){
        this.cursos = cursos;
    }

    public List<Curso> getCursos(){
        return cursos;
    }

    public boolean agregarCurso(Curso curso){
        return cursos.add(curso);
    }

    public boolean eliminarCurso(final Curso curso){
        return cursos.removeIf( (Curso c)->
                                c.getId().equals( curso.getId() ) );
    }

    public void setDocuments(Set<CloudDocument> docs){
        this.documents = docs;
    }

    public Set<CloudDocument> getDocuments(){
        return documents;
    }

    public boolean agregarDocumentos(CloudDocument... docs){
        return documents.addAll( Arrays.asList(docs) );
    }

    public boolean agregarDocumento(CloudDocument doc){
        return documents.add(doc);
    }

    public boolean eliminarDocumento(final CloudDocument doc){
        return documents.removeIf( (d)->
                                    d.equals(doc) );
    }
}
