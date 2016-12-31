package ug.monografico32.model;

import javax.persistence.Entity;

/**
 * Created by Jose Elias on 28/11/2016.
 */
@Entity
public class AmazonS3Document extends CloudDocument{

    public AmazonS3Document(String key, DocumentType dType) {
        super(key, dType);
    }
}
