package ug.monografico32.model;

import javax.validation.constraints.Size;

/**
 * Created by Jose Elias on 25/10/2016.
 */
public class Estudiante extends Persona{
    
    @Size(min = 1, max =1)
    private String seccion;
}
