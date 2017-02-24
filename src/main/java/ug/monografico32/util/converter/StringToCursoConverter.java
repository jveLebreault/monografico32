package ug.monografico32.util.converter;

import org.springframework.core.convert.converter.Converter;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.model.Curso;

/**
 * Created by Jose Elias on 24/02/2017.
 */
public class StringToCursoConverter implements Converter<String, Curso> {

    private CursoRepository repository;

    public StringToCursoConverter(CursoRepository repository){
        this.repository = repository;
    }

    @Override
    public Curso convert(String s) {
        Long id = Long.parseLong(s);
        return repository.findById(id);
    }
}
