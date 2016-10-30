/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model;

import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
public class Curso {
    
    private Nivel nivel;
    
    //private Grado
    
    @Size(min = 1, max =1)
    private String seccion;
    
    
    public void setNivel(Nivel nivel){
        this.nivel = nivel;
    }
    
    public Nivel getNivel(){
        return nivel;
    }
}
