package ug.monografico32.model;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import javax.validation.Valid;

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
    @Valid
    private List<Tutor> tutores;

    private boolean esTransferido;
    private CloudDocument recordNotaTransferido;
    private CloudDocument certificadoBuenaConductaTransferido;
    
    protected Estudiante(){}
    
    public Estudiante(String nombres, String apellidos, CloudDocument foto, 
                CloudDocument certificadoMedico, CloudDocument actaNacimiento){
        super(nombres,apellidos);
        this.foto = foto;
        this.certificadoMedico = certificadoMedico;
        this.actaNacimiento = actaNacimiento;
        tutores = new ArrayList<>();
        this.esTransferido = false;
    }
    
    public Estudiante(String nombres, String apellidos, CloudDocument foto, 
                CloudDocument certificadoMedico, CloudDocument actaNacimiento, 
                CloudDocument recordNotaTransferido, 
                CloudDocument certificadoConductaTransferido){
        
        this(nombres, apellidos, foto, certificadoMedico, actaNacimiento);
        this.esTransferido = true;
        this.recordNotaTransferido = recordNotaTransferido;
        this.certificadoBuenaConductaTransferido = certificadoConductaTransferido;
    }
    
    public void setTutores(List<Tutor> tutores){
        this.tutores = tutores;
    }
    
    public List<Tutor> getTutores(){
        return tutores;
    }
    
    public boolean agregarTutor(List<Tutor> tutores){
        return tutores.addAll(tutores);
    }
    
    public boolean agregarTutor(Tutor tutor){
        return tutores.add(tutor);
    }
    
    public boolean eliminarTutor(final Tutor tutor){
        return tutores.removeIf( (Tutor t)-> 
                                t.getId().equals(tutor.getId() ) );
    }
}
