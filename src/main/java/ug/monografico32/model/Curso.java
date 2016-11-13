/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
//TODO: Make custom validation constraint for Grado and Fecha
//TODO: 
public class Curso {

    @Id @GeneratedValue
    private Long id;
    
    @NotNull
    private Nivel nivel;
    
    @NotNull
    private Grado grado;
    
    @Size(min = 1, max =5)
    @NotNull
    private String seccion;
    
    //@NotNull
    private List<Estudiante> estudiantes;
    
    @NotNull
    private Docente docenteEncargado;
    //private Set<Asignaturas> asignaturas;
    
    @Future
    private LocalDate fechaInicio;
    
    @Future
    private LocalDate fechaFinal;
    
    private void validarGrado() throws GradoException{
        switch(nivel){
            case INICIAL:
                if( this.grado != Grado.KINDER || 
                    this.grado != Grado.PRE_PRIMARIO ){
                        throw new GradoException("Nivel inicial no puede"
                                + " tener grado: "+this.grado);
                }
                break;
            case BASICA:
                if(this.grado == Grado.KINDER || 
                   this.grado == Grado.PRE_PRIMARIO){
                        throw new GradoException("Nivel Bascio no puede"
                                + " tener grado: "+this.grado);
                }
            case MEDIA:
                if( this.grado.getOrdinal()>4 || this.grado == Grado.KINDER || 
                    this.grado == Grado.PRE_PRIMARIO){
                        throw new GradoException("Nivel Media no puede"
                                + " tener grado: "+this.grado);
                }
            default:
                break;
        }
    }
    
    private void validarFechas(){
        if(fechaInicio.isAfter(fechaFinal)){
            throw new DateTimeException("Fecha Inicio es mayor a fecha Final");
        }
    }
    
    public Curso(){}
    
    public Curso(Nivel nivel, Grado grado, String seccion, Docente encargado,
                LocalDate inicio, LocalDate termino) throws GradoException{
        this.nivel = nivel;
        this.grado = grado;
        this.seccion = seccion;
        this.docenteEncargado = encargado;
        this.fechaFinal = inicio;
        this.fechaFinal = termino;
        estudiantes = new ArrayList<>();
        validarGrado();
        validarFechas();
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setNivel(Nivel nivel) throws GradoException{
        this.nivel = nivel;
        validarGrado();
    }
    
    public Nivel getNivel(){
        return nivel;
    }
    
    public void setGrado(Grado grado) throws GradoException{
        this.grado = grado;
        validarGrado();
    }
    
    public Grado getGrado(){
        return grado;
    }

    public void setSeccion(String seccion){
        this.seccion = seccion;
    }

    public String getSeccion(){
        return seccion;
    }
    
    public void setEstudiantes(List<Estudiante> estudiantes){
        this.estudiantes = estudiantes;
    }
    
    public List<Estudiante> getEstudiantes(){
        return estudiantes;
    }
    
    public boolean agregarEstudiante(Estudiante estudiante){
        return estudiantes.add(estudiante);
    }
    
    public boolean eliminarEstudiante(final Estudiante estudiante){
        //final long estId = estudiante.getId();
        return estudiantes.removeIf( (Estudiante e) -> 
                                        e.getId() == estudiante.getId() );
    }
    
    public void setDocenteEncargado(Docente encargado){
        this.docenteEncargado = encargado;
    }
    
    public Docente getDocenteEncargado(){
        return docenteEncargado;
    }
    
    public void setFechaInicio(LocalDate inicio){
        this.fechaInicio = inicio;
        validarFechas();
    }
    
    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    
    public void setFechaFinal(LocalDate fin){
        this.fechaFinal = fin;
        validarFechas();
    }
    
    public LocalDate getFechaFinal(){
        return fechaFinal;
    }
    
}

class GradoException extends Exception{
    
    public GradoException(String message){
        super(message);
    }
}
