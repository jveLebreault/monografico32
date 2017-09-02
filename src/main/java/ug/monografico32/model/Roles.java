package ug.monografico32.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority{
    ADMINISTRADOR,
    DOCENTE,
    ESTUDIANTE;

    @Override
    public String getAuthority(){
        return this.toString();
    }
}
