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
    private AmazonS3Document cedula;

    public Tutor(){
        super();
    }

    public Tutor(String nombres, String apellidos){
        super(nombres, apellidos);
    }

    public void setCedula(AmazonS3Document cedula){
        this.cedula = cedula;
    }

    public AmazonS3Document getCedula(){
        return cedula;
    }
}
