package ug.monografico32.model;

import java.io.IOException;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.model.aws.URLSignerWrapper;

/**
 * Created by Jose Elias on 28/11/2016.
 */
@Entity
public class AmazonS3Document extends CloudDocument implements Serializable{
    
    @Autowired
    private transient URLSignerWrapper urlSigner;

    public AmazonS3Document(String key, DocumentType dType) {
        super(key, dType);
    }
    
    public AmazonS3Document(){
        super();
    }
    
    public String getDocumentURL() throws InvalidKeySpecException, IOException{
        return urlSigner.getURLFor(this.getDocumentKey());
    }
}
