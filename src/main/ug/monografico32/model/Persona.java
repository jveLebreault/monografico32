package ug.monografico32.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Jose Elias on 25/10/2016.
 */
public class Persona {

    private Long id;

    //Validation constraints to be applied to these attributes
    @NotNull
    @Size(min = 2, max = 50)
    private String nombres;

    @NotNull
    @Size(min = 2, max = 50)
    private String apellidos;

    public Persona(){}

    public Persona(Long id, String nombres, String apellidos){
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
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
}