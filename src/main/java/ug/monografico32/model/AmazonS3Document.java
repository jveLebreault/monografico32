package ug.monografico32.model;

import java.io.IOException;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.model.aws.URLSignerWrapper;

/**
 * Created by Jose Elias on 28/11/2016.
 */
//TODO: Usea a bean to get the file URL instead
@Entity
public class AmazonS3Document extends CloudDocument implements Serializable{
    
    @Autowired
    @Transient
    private URLSignerWrapper urlSigner;

    public AmazonS3Document(URLSignerWrapper urlSigner){
        super();
        this.urlSigner = urlSigner;
    }

    public AmazonS3Document(String key, DocumentType dType) {
        super(key, dType);
    }

    public AmazonS3Document(String key, DocumentType dType, URLSignerWrapper urlSigner){
        super(key, dType);
        this.urlSigner = urlSigner;
    }
    
    public AmazonS3Document(){
        super();
    }
    
    public String getDocumentURL() throws InvalidKeySpecException, IOException{
        return urlSigner.getURLFor(this.getDocumentKey());
    }
}
