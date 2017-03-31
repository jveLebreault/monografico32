/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.model.Docente;

/**
 *
 * @author Administrador
 */
@Component
public class StringToDocenteConverter implements Converter<String, Docente>{
        
    @Autowired
    private DocenteRepository repository;
    
    public StringToDocenteConverter(DocenteRepository repo){
        this.repository = repo;
    }
    
    @Override
    public Docente convert(String s) {
        Long id = Long.parseLong(s);
        return repository.findById(id);
    }
    
}
