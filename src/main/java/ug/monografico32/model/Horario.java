package ug.monografico32.model;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by Jose Elias on 28/01/2017.
 */
//TODO: Create test for this class
//@Entity
public class Horario {
    
    //@Id @GeneratedValue
    private Long id;
    
    private List<Clase> clases;
    
    @Transient
    private List<Sesion> colisiones;
    {colisiones = new ArrayList<>();}

    public List<Clase> getClases(){
        return clases;
    }

    public void setClases(List<Clase> clases){
        this.clases = clases;
    }


    public boolean agregarClase(Clase newClase){

        List<Sesion> sesionList = getAllSesions();

        colisiones = newClase.getSesiones().stream().
                filter(sesion -> sesionList.contains(sesion)).
                collect(Collectors.toList());

        if( colisiones.size() > 0){
            return false;
        }

        if( clases.contains(newClase) ){
            int index = clases.indexOf(newClase);
            Clase clase = clases.get(index);
            return clase.agregarSesion( newClase.getSesiones() );
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
        //return getAllSesions().stream().collect( Collectors.groupingBy( Sesion::getDia ) );
        return clases.stream().flatMap(clase -> clase.getSesiones().stream()).
                collect( Collectors.groupingBy( Sesion::getDia));
    }

    public List<Sesion> getAllSesions(){
        /*return clases.stream().collect( ArrayList::new,
                (ArrayList list, Clase clase) -> list.addAll( clase.getSesiones()),
                 ArrayList::addAll );*/
        return clases.stream().flatMap(clase -> clase.getSesiones().stream()).
                collect(Collectors.toList());
    }
}
