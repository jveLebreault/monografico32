package ug.monografico32.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Jose Elias on 24/11/2016.
 */
public abstract class CloudDocument {

    @Id
    @NotNull
    private String documentKey;

    @NotNull
    private DocumentType documentType;

    public void setDocumentURL(String documentKey){
        this.documentKey = documentKey;
    }
    
    public String getDocumentURL(){
        return documentKey;
    }

    public void setDocumentType(DocumentType type){
        this.documentType = type;
    }

    public DocumentType getDocumentType(){
        return documentType;
    }
}
