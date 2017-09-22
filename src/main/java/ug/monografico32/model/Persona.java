package ug.monografico32.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jose Elias on 25/10/2016.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona implements Serializable {

    @Id @GeneratedValue
    private Long id;

    //Validation constraints to be applied to these attributes
    @NotNull
    @Size(min = 2, max = 50, message = "{LongitudNombres.message}")
    private String nombres;

    @NotNull
    @Size(min = 2, max = 50, message = "{LongitudApellidos.message}")
    private String apellidos;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Estado estado; //activo, inactivo etc...

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AmazonS3Document> documents;
    {documents = new HashSet<>();}

    public Persona(){
        this.estado = Estado.ACTIVO;
    }

    public Persona(Long id, String nombres, String apellidos){
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estado = Estado.ACTIVO;
    }

    public Persona(String nombres, String apellidos){
        this(null, nombres, apellidos);
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setNombres(String nombres){
        this.nombres = nombres;
    }

    public String getNombres(){
        return nombres;
    }

    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }

    public String getApellidos(){
        return apellidos;
    }

    public String getNombreCompleto(){
        return nombres+" "+apellidos;
    }
    
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    
    public Estado getEstado(){
        return estado;
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
}
