package ug.monografico32.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ug.monografico32.dao.CursoRepository;
import ug.monografico32.model.Curso;

@Component
public class StringToCursoConverter implements Converter<String, Curso> {

    private CursoRepository repository;

    public StringToCursoConverter(CursoRepository repository){
        this.repository = repository;
    }

    @Override
    public Curso convert(String s) {
        Long id = Long.parseLong(s);
        return repository.findByIdAndFetchHorario(id);
    }
}
