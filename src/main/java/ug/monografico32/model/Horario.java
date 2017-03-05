package ug.monografico32.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by Jose Elias on 28/01/2017.
 */
//@Entity
public class Horario {
    
    //@Id @GeneratedValue
    private Long id;
    
    
    private Set<Clase> clases;
    
    @Transient
    private List<Sesion> colisiones;
    {colisiones = new ArrayList<>();}

    public Set<Clase> getClases(){
        return clases;
    }

    public void setClases(Set<Clase> clases){
        this.clases = clases;
    }

    public boolean agregarClase(Clase newClase){
        List<Sesion> colisiones = clases.stream().collect(ArrayList::new,
                                (ArrayList list, Clase clase)->list.add( clase.getSesiones() ), 
                                ArrayList::addAll).
                                stream().filter( Sesion::checkForCollision ).
                                collect(Collectors.toList());
        if(colisiones.size() > 0){
            this.colisiones = colisiones;
            return false;
        }
        return clases.add(newClase);
    }

    public boolean eliminarClase(final Clase clase){
        return clases.remove( clase);
    }
    
    public List<Sesion> getColisiones(){
        return colisiones;
    }
}
