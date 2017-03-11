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
    {clases = new ArrayList<>();}
    
    @Transient
    private List<Sesion> colisiones;
    {colisiones = new ArrayList<>();}

    public Horario(){}

    public Horario(List<Clase> clases){
        this.clases = clases;
    }

    public List<Clase> getClases(){
        return clases;
    }

    public void setClases(List<Clase> clases){
        this.clases = clases;
    }


    public boolean agregarClase(Clase newClase){
        boolean doesCollides = checkForColisions(newClase);
        if( clases.contains(newClase) ){
            int index = clases.indexOf(newClase);
            Clase clase = clases.get(index);
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
                collect( Collectors.groupingBy( Sesion::getDia));
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
