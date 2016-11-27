package ug.monografico32.model;

/**
 * Created by Jose Elias on 24/11/2016.
 */
public abstract class CloudDocument {
    
    private String documentURL;
    
    public void setDocumentURL(String documentURL){
        this.documentURL = documentURL;
    }
    
    public String getDocumentURL(){
        return documentURL;
    }
}
