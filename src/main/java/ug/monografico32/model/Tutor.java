package ug.monografico32.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by Jose Elias on 24/11/2016.
 */
@Entity
public class Tutor extends Persona implements Serializable{

    @OneToOne( cascade = CascadeType.ALL ,orphanRemoval = true)
    private CloudDocument cedula;

    public Tutor(){
        super();
    }

    public Tutor(String nombres, String apellidos){
        super(nombres, apellidos);
    }

    public void setCedula(CloudDocument cedula){
        this.cedula = cedula;
    }

    public CloudDocument getCedula(){
        return cedula;
    }
}
