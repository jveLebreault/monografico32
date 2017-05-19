package ug.monografico32.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ug.monografico32.dao.PeriodoRepository;
import ug.monografico32.model.Periodo;

/**
 * Created by Jose Elias on 18/05/2017.
 */
@Component
public class StringToPeriodoConverter implements Converter<String, Periodo> {

    @Autowired
    private PeriodoRepository repository;

    public StringToPeriodoConverter(PeriodoRepository repository){
        this.repository = repository;
    }

    @Override
    public Periodo convert(String s) {
        Long id = Long.parseLong(s);
        return repository.findOne(id);
    }
}
