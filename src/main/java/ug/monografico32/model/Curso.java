/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ug.monografico32.model.validation.constraints.annotations.CursoGradoValido;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

/**
 *
 * @author Administrador
 */
@CursoGradoValido
@FechaInicioFinalValida
@Entity
public class Curso {

    @Id @GeneratedValue
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Nivel nivel;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Grado grado;
    
    @Size(min = 1, max =5)
    @NotNull
    private String seccion;
    
    @ManyToMany( cascade = CascadeType.ALL)
    @Valid
    private List<Estudiante> estudiantes;
    
    @NotNull
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private Docente docenteEncargado;
    //private Set<Asignaturas> asignaturas;
    
    @Future
    @NotNull
    //@Temporal( TemporalType.DATE )
    private Instant fechaInicio;
    
    @Future
    @NotNull
    //@Temporal( TemporalType.DATE )
    private Instant fechaFinal;
    
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
    }
    
    public Instant getFechaInicio(){
        return fechaInicio;
    }
    
    public void setFechaFinal(Instant fin){
        this.fechaFinal = fin;
    }
    
    public Instant getFechaFinal(){
        return fechaFinal;
    }
    
}
