package ug.monografico32.dao.specification;

import org.springframework.data.jpa.domain.Specification;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Grado;
import ug.monografico32.model.Nivel;

import javax.persistence.criteria.*;

public class CursoSpecification {

    public static Specification<Curso> periodoHasYear(Integer year){

        return (Root<Curso> curso, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
                Expression<? extends Number> fechaInicioYear = builder.function("YEAR", Integer.class, curso.get("periodo").get("fechaInicio"));
                Expression<? extends Number> fechaFinalYear = builder.function("YEAR", Integer.class, curso.get("periodo").get("fechaFinal"));
                query.distinct(true);

                if(year == null)
                    return builder.ge(fechaInicioYear, Integer.valueOf(0));
                else
                    return builder.or(builder.equal(fechaInicioYear, year),
                            builder.equal(fechaFinalYear, year));
        };
    }

    public static Specification<Curso> hasNivel(Nivel nivel){
        if(nivel == null)
            return null;
        return (Root<Curso> curso, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.equal(curso.get("nivel"), nivel);
    }

    public static Specification<Curso> hasGrado(Grado grado){
        if(grado == null)
            return null;
        return (Root<Curso> curso, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.equal(curso.get("grado"), grado);
    }

    public static Specification<Curso> hasSeccion(String seccion){
        if(seccion == null || seccion.trim().equals(""))
            return null;
        return (Root<Curso> curso, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.equal(curso.get("seccion"), seccion);
    }
}
