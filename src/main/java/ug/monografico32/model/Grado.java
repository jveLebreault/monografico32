/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

/**
 *
 * @author Administrador
 */
public enum Grado {
    KINDER("Kinder", 1),
    PRE_PRIMARIO("Pre Primario" ,2),
    PRIMERO("1ero",1),
    SEGUNDO("2do",2),
    TERCERO("3ero",3),
    CUARTO("4to",4),
    QUINTO("5to",5),
    SEXTO("6to",6),
    SEPTIMO("7mo",7),
    OCTAVO("8vo",8);
    
    
    private final String literal;
    private final int ordinal;
    
    
    private Grado(String literal, int ordinal){
        this.literal = literal;
        this.ordinal = ordinal;
    }
    
    public String getLiteral(){
        return this.literal;
    }
    
    public int getOrdinal(){
        return this.ordinal;
    }
    
}
