package ug.monografico32.dao.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Estudiante;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EstudianteSpecification {

    public static Specification<Estudiante> estudianteNotInCurso(Curso curso){

        return (Root<Estudiante> estudiante, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.isNotMember(curso, estudiante.get("cursos"));
    }

    public static Specification<Estudiante> hasNombres(String nombres){
        if( nombres == null || nombres.trim().equals(""))
            return null;

        return (Root<Estudiante> estudiante, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.like(
                        builder.lower(estudiante.get("nombres")),
                        "%"+nombres.toLowerCase()+"%");

    }

    public static Specification<Estudiante> hasApellidos(String apellidos){
        if( apellidos == null || apellidos.trim().equals(""))
            return null;

        return (Root<Estudiante> estudiante, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> builder.like(
                        builder.lower(estudiante.get("apellidos")),
                        "%"+apellidos.toLowerCase()+"%");
    }

    public static  Specification<Estudiante> byNombresApellidosNotInCurso(Estudiante estudiante, Curso curso){
         return Specifications.where(estudianteNotInCurso(curso)).
                    and(hasNombres(estudiante.getNombres())).
                    and(hasApellidos(estudiante.getApellidos()));
    }
}
