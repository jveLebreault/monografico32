package ug.monografico32.util;

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

/**
 * Created by Jose Elias on 20/03/2017.
 */
public class HorarioFlowUtils {

    public static boolean eliminarSesion(RequestContext context){

        ParameterMap params = context.getRequestParameters();

        if (params.contains("dia") && params.contains("horaInicio") &&
                params.contains("horaFinal")) {
            Curso curso = (Curso) context.getFlowScope().get("curso");
            if(curso.getHorario() == null){
                System.out.print("\n\n\n\\nHORARIO ES NULL\n\n\n");
            }
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
}
