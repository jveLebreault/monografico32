/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Docente;

/**
 *
 * @author Administrador
 */
public class LongToDocente implements Converter<String, Docente>{
        
    @Autowired
    private DocenteRepository repository;
    
    public LongToDocente(DocenteRepository repository){
        this.repository = repository;
    }
    
    @Override
    public Docente convert(String s) {
        if((s == null) || s.isEmpty() )
            throw new IllegalArgumentException();
        if( repository == null)
            System.out.println("Repository null");
        
        Long id = Long.parseLong(s);
        System.out.println("LongToDocente: "+id);
        return repository.findById(id);
    }
    
}
