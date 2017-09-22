package ug.monografico32.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ug.monografico32.dao.ClaseRepository;
import ug.monografico32.model.Clase;

@Component
public class StringToClaseConverter implements Converter<String, Clase> {

    @Autowired
    private ClaseRepository repo;

    public StringToClaseConverter(ClaseRepository claseRepository){
        this.repo = claseRepository;
    }

    @Override
    public Clase convert(String s) {
        Long id = Long.parseLong(s);
        return repo.findOne(id);
    }
}
