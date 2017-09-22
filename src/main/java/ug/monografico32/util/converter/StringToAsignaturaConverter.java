/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ug.monografico32.dao.AsignaturaRepository;
import ug.monografico32.model.Asignatura;

@Component
public class StringToAsignaturaConverter implements Converter<String, Asignatura>{
    
    @Autowired
    private AsignaturaRepository repo;
    
    public StringToAsignaturaConverter(AsignaturaRepository repo){
        this.repo = repo;
    }
    
    @Override
    public Asignatura convert(String s) {
        Long id = Long.parseLong(s);
        return repo.findOne(id);
    }
    
    
    
}
