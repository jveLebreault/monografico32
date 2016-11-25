package ug.monografico32.model;

import javax.validation.constraints.NotNull;

/**
 * Created by Jose Elias on 24/11/2016.
 */
public class EstudianteMedia extends Estudiante {

    @NotNull
    private CloudDocument certificadoEducacionBasica;
}
