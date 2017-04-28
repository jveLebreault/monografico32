package ug.monografico32.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.persistence.*;

/**
 * Created by Jose Elias on 28/01/2017.
 */
@Entity
public class Horario implements Serializable{
    
    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "horario")
    private Curso curso;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clase> clases;
    {clases = new HashSet<>();}
    
    @Transient
    private List<Sesion> colisiones;
    {colisiones = new ArrayList<>();}

    public Horario(){}

    public Horario(Set<Clase> clases){
        this.clases = clases;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Set<Clase> getClases(){
        return clases;
    }

    public void setClases(Set<Clase> clases){
        this.clases = clases;
    }


    public boolean agregarClase(Clase newClase){
        boolean doesCollides = checkForColisions(newClase);
        if( clases.contains(newClase) ){

            Clase clase = clases.stream().
                    filter(c -> c.equals(newClase)).findFirst().
                    orElse(newClase);

            return clase.agregarSesion( newClase.getSesiones() );
        }
        else if(doesCollides){
            return false;
        }
            
        return clases.add(newClase);
    }

    public boolean eliminarClase(final Clase clase){
        return clases.remove( clase );
    }
    
    public List<Sesion> getColisiones(){
        return colisiones;
    }

    public Map<DayOfWeek, List<Sesion>> getSesionsByDayOfWeek(){
        return clases.stream().flatMap(clase -> clase.getSesiones().stream()).
                collect( Collectors.
                        groupingBy( Sesion::getDia, TreeMap::new, Collectors.toList()));
    }

    public List<Sesion> getAllSesions(){
        return clases.stream().flatMap(clase -> clase.getSesiones().stream()).
                collect(Collectors.toList());
    }

    public boolean checkForColisions(Clase newClase){
        List<Sesion> sesionList = getAllSesions();

        colisiones = newClase.getSesiones().stream().
                filter(sesion -> sesionList.contains(sesion)).
                collect(Collectors.toList());
        return !colisiones.isEmpty();
    }

    public void clearColisiones(){
        if ( !colisiones.isEmpty() )
            colisiones.clear();
    }


}
