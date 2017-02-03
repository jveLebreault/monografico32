package ug.monografico32.model;

/**
 * Created by Jose Elias on 28/01/2017.
 */
public class Asignatura {

    private String nombre;
    private String clave;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
}
