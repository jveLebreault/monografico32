package ug.monografico32.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jose Elias on 28/01/2017.
 */
public class Horario {
    
    private Map<Clase, List<Sesion>> clazz;
    private Set<Clase> clases;

    public Set<Clase> getClases(){
        return clases;
    }

    public void setClases(Set<Clase> clases){
        this.clases = clases;
    }

    public boolean agregarClase(Clase clase){
        return clases.add(clase);
    }

    //TODO: implement equals method for Clase
    public boolean eliminarClase(final Clase clase){
        return clases.remove( clase);
    }
}
