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
public enum Estado {

    ACTIVO ("Activo"),
    INACTIVO ("Inactivo"),
    PENDIENTE_APROBACION ("Pendiente de aprobacion");

    private String literal;

    private Estado(String l) {
        this.literal = l;
    }

    @Override
    public String toString() {
        return literal;
    }
}
