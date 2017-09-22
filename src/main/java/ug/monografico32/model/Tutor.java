package ug.monografico32.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Tutor extends Persona implements Serializable{

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 13)
    private String numeroCedula;

    public Tutor(){
        super();
    }

    public Tutor(String nombres, String apellidos, String nroCedula){
        super(nombres, apellidos);
        this.numeroCedula = nroCedula;
    }

    public void setCedula(AmazonS3Document cedula){
        this.agregarDocumento(cedula);
    }

    public AmazonS3Document getCedula(){
        return this.getDocuments().
                stream().filter( doc -> doc.getDocumentType().equals(DocumentType.CEDULA)).
                findFirst().get();
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutor tutor = (Tutor) o;

        return numeroCedula.equals(tutor.numeroCedula);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroCedula);
    }
}
