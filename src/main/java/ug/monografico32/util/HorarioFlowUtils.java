package ug.monografico32.util;

import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.execution.RequestContext;
import ug.monografico32.model.Clase;
import ug.monografico32.model.Curso;
import ug.monografico32.model.Sesion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Jose Elias on 20/03/2017.
 */
public class HorarioFlowUtils {

    public static boolean eliminarSesion(RequestContext context){

        ParameterMap params = context.getRequestParameters();

        if (params.contains("dia") && params.contains("horaInicio") &&
                params.contains("horaFinal")) {
            Clase clase = (Clase) context.getFlowScope().get("clase");

            DayOfWeek dia = DayOfWeek.valueOf(params.get("dia"));

            String incio = params.get("horaInicio");
            Date horaInicio = new Date(Long.parseLong(incio));

            String fin = params.get("horaFinal");
            Date horaFinal = new Date(Long.parseLong(fin));

            Sesion sesion = new Sesion(dia, horaInicio, horaFinal);

            return clase.removerSesion(sesion);
        }
        return false;
    }

    public static String selectInitialState(RequestContext context){
        ParameterMap params = context.getRequestParameters();

        if( params.contains("curso") )
            return "procesar-curso";

        if( params.contains("clase") )
            return "procesar-clase";

        return "error";
    }

    /**
     *
     * @param context
     * Verifica is existe una clase con la misma asignatura que la clase en 
     * FlowScope, de ser asi pone en FlowScope la Clase existente con sesiones creadas
     */
    public static void validateClase(RequestContext context){
        MutableAttributeMap attrs = context.getFlowScope();
        Curso curso = (Curso) attrs.get("curso");
        Clase clase = (Clase) attrs.get("clase");

        Optional<Clase> result =  curso.getHorario().getClases().stream().
                filter(c -> c.getAsignatura().equals(clase.getAsignatura()) ).
                findFirst();

        result.ifPresent(c -> attrs.put("clase", c));
    }
    
    public static void populateFlowScopeFromClase(RequestContext context){
        MutableAttributeMap attrs = context.getFlowScope();
        Clase clase = (Clase) attrs.get("clase");
        
        attrs.put("curso", clase.getHorario().getCurso());
    }
}
