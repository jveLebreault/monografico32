package ug.monografico32.model;

import java.io.IOException;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import ug.monografico32.model.aws.URLSignerWrapper;

@Entity
public class AmazonS3Document extends CloudDocument implements Serializable{

    public AmazonS3Document(String key, DocumentType dType) {
        super(key, dType);
    }
    
    public AmazonS3Document(){
        super();
    }

}
