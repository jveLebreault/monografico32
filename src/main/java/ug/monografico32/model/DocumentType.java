package ug.monografico32.model;

public enum DocumentType {

    FOTO("Foto"),
    CERTIFICADO_MEDICO("Certificado medico"),
    ACTA_NACIMIENTO("Acta de nacimiento"),
    RECORD_NOTA("Record de notas"),
    CEDULA("Cedula"),
    CERTIFICADO_BUENA_CONDUCTA("Certificado de buena conducta");

    private String literal;

    private DocumentType(String literal){
        this.literal = literal;
    }

    public String getLiteral(){
        return literal;
    }
}
