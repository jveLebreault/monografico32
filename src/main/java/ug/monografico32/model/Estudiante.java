package ug.monografico32.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Jose Elias on 25/10/2016.
 */
//TODO: A custom validation to validate the existence of documents from transfered students
public class Estudiante extends Persona{

    @NotNull
    private CloudDocument foto;

    @NotNull
    private CloudDocument certificadoMedico;

    @NotNull
    private CloudDocument actaNacimiento;

    @NotNull
    @Size(min = 1)
    private List<Tutor> tutores;

    private boolean esTransferido;
    private CloudDocument recordNotaTransferido;
    private CloudDocument certificadoBuenaConductaTransferido;

}
