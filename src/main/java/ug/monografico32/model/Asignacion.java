package ug.monografico32.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Asignacion implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 5, max = 40)
    private String nombre;

    @NotNull
    @Size(min = 5, max = 250)
    private String descripcion;

    @NotNull
    @Temporal( TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;

    @NotNull
    @Temporal( TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private Date fechaEntrega;

    @Min(1)
    private int valor;

    @ManyToOne
    private Clase clase;

    @ManyToMany
    private Set<Estudiante> asignados;

    @OneToMany(mappedBy = "asignacion")
    private Set<Calificacion> calificaciones;

    public Asignacion(){
        fechaCreacion = new Date();
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Set<Estudiante> getAsignados() {
        return asignados;
    }

    public void setAsignados(Set<Estudiante> asignados) {
        this.asignados = asignados;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Set<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(Set<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
