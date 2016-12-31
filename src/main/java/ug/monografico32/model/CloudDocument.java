package ug.monografico32.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Jose Elias on 24/11/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CloudDocument {

    @Id
    @NotNull
    private String documentKey;

    @NotNull
    private DocumentType documentType;

    @Override
    public boolean equals(Object obj){
        if( !(obj instanceof CloudDocument) )
            return false;

        if(obj == this)
            return true;

        CloudDocument doc = (CloudDocument) obj;

        return( this.documentKey == doc.getDocumentKey() );
    }

    @Override
    public int hashCode() {
        return documentKey.hashCode();
    }

    public CloudDocument(String key, DocumentType dType){
        this.documentKey = key;
        this.documentType = dType;
    }
    
    public CloudDocument(DocumentType dType){
        this.documentType = dType;
    }

    public void setDocumentKey(String documentKey){
        this.documentKey = documentKey;
    }
    
    public String getDocumentKey(){
        return documentKey;
    }

    public void setDocumentType(DocumentType type){
        this.documentType = type;
    }

    public DocumentType getDocumentType(){
        return documentType;
    }
}
