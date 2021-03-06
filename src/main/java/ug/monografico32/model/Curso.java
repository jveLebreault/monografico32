/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import ug.monografico32.model.validation.constraints.annotations.CursoGradoValido;
import ug.monografico32.model.validation.constraints.annotations.FechaInicioFinalValida;

@CursoGradoValido
@Entity
public class Curso implements Serializable {

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
    
    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Valid
    private Set<Estudiante> estudiantes;
    {estudiantes = new HashSet<>();}

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.MERGE)
    private Docente docenteEncargado;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    private Periodo periodo;
    
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "curso")
    private Horario horario;
    {
        horario = new Horario();
        horario.setCurso(this);
    }

    
    public Curso(){}

    public Curso(Nivel nivel, Grado grado, String seccion, Docente encargado,
                 Periodo periodo){
        this.nivel = nivel;
        this.grado = grado;
        this.seccion = seccion;
        this.docenteEncargado = encargado;
        this.periodo = periodo;
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
    
    public void setEstudiantes(Set<Estudiante> estudiantes){
        this.estudiantes = estudiantes;
    }
    
    public Set<Estudiante> getEstudiantes(){
        return estudiantes;
    }
    
    public boolean agregarEstudiante(Estudiante estudiante){
        return estudiantes.add(estudiante);
    }
    
    public boolean eliminarEstudiante(final Estudiante estudiante){
        return estudiantes.removeIf( (Estudiante e) -> 
                                        e.getId().equals( estudiante.getId() ));
    }
    
    public void setDocenteEncargado(Docente encargado){
        this.docenteEncargado = encargado;
    }
    
    public Docente getDocenteEncargado(){
        return docenteEncargado;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public void setHorario(Horario horario){
        this.horario = horario;
    }
    
    public Horario getHorario(){
        return horario;
    }

    public String getGradoSeccion(){
        return getGrado().getLiteral()+" "+getSeccion();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(obj == this)
            return true;

        if(obj instanceof Curso){
            Curso curso = (Curso) obj;

            return ( (this.periodo.equals(curso.getPeriodo()) ) &&
                     (this.getNivel().equals(curso.getNivel())) &&
                     (this.getGrado().equals(curso.getGrado())) &&
                     (this.getSeccion().equals(curso.getSeccion()))
                    );
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nivel, grado, seccion, periodo);
    }
}
