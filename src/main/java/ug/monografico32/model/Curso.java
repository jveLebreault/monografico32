/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ug.monografico32.model.annotations.CursoGradoValido;
import ug.monografico32.model.annotations.FechaInicioFinalValida;

/**
 *
 * @author Administrador
 */
//TODO: create fechaInicio and fechaFinal validators
@CursoGradoValido
@FechaInicioFinalValida
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
    @Valid
    private List<Estudiante> estudiantes;
    
    @NotNull
    @Valid
    private Docente docenteEncargado;
    //private Set<Asignaturas> asignaturas;
    
    @Future
    @NotNull
    private Instant fechaInicio;
    
    @Future
    @NotNull
    private Instant fechaFinal;
    
    
    private void validarFechas(){
        if(this.fechaInicio.isAfter(this.fechaFinal)){
            throw new DateTimeException("Fecha Inicio es mayor a fecha Final");
        }
    }
    
    public Curso(){}
    
    public Curso(Nivel nivel, Grado grado, String seccion, Docente encargado,
                Instant inicio, Instant termino){
        this.nivel = nivel;
        this.grado = grado;
        this.seccion = seccion;
        this.docenteEncargado = encargado;
        this.fechaInicio = inicio;
        this.fechaFinal = termino;
        estudiantes = new ArrayList<>();
        //validarFechas();
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setNivel(Nivel nivel){
        this.nivel = nivel;
    }
    
    public Nivel getNivel(){
        return nivel;
    }
    
    public void setGrado(Grado grado){
        this.grado = grado;
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
                                        e.getId().equals( estudiante.getId() ));
    }
    
    public void setDocenteEncargado(Docente encargado){
        this.docenteEncargado = encargado;
    }
    
    public Docente getDocenteEncargado(){
        return docenteEncargado;
    }
    
    public void setFechaInicio(Instant inicio){
        this.fechaInicio = inicio;
        validarFechas();
    }
    
    public Instant getFechaInicio(){
        return fechaInicio;
    }
    
    public void setFechaFinal(Instant fin){
        this.fechaFinal = fin;
        validarFechas();
    }
    
    public Instant getFechaFinal(){
        return fechaFinal;
    }
    
}
