package ug.monografico32.model;

import java.util.Objects;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CloudDocument {

    @Id
    @NotNull
    @Column(updatable = false)
    private String documentKey;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    public CloudDocument(String key, DocumentType dType){
        this.documentKey = key;
        this.documentType = dType;
    }
    
    public CloudDocument(DocumentType dType){
        this.documentType = dType;
    }
    
    public CloudDocument(){}

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

    @Override
    public boolean equals(Object obj){
        if( !(obj instanceof CloudDocument) )
            return false;

        if(obj == this)
            return true;

        CloudDocument doc = (CloudDocument) obj;

        return( this.documentKey.equals( doc.getDocumentKey() ));
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentKey);
    }
    
}
