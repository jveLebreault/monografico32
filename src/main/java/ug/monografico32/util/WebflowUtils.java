package ug.monografico32.util;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.webflow.execution.RequestContext;
import ug.monografico32.model.DocumentType;

import java.util.*;

import org.springframework.webflow.core.collection.ParameterMap;
import ug.monografico32.model.Tutor;

public class WebflowUtils {

    public static EnumMap getEstudianteFiles(RequestContext context) throws IOException{
        ParameterMap parameters = context.getRequestParameters();
        
        EnumMap<DocumentType, byte[]> documents = new EnumMap<>(DocumentType.class);
        
        documents.put(DocumentType.FOTO, 
                parameters.getRequiredMultipartFile("estudiante-foto").getBytes());
        
        documents.put(DocumentType.CERTIFICADO_MEDICO, 
                parameters.getRequiredMultipartFile("certificado-Medico").getBytes());
        
        documents.put(DocumentType.ACTA_NACIMIENTO, 
                parameters.getRequiredMultipartFile("acta-Nacimiento").getBytes());
        
        
        if( parameters.getBoolean("transferido", false) ){
            documents.put(DocumentType.CERTIFICADO_BUENA_CONDUCTA, 
                    parameters.getRequiredMultipartFile("certificadoBuenaConducta").getBytes());
            
            documents.put(DocumentType.RECORD_NOTA, 
                    parameters.getRequiredMultipartFile("record-NotaTransferido").getBytes());
        }
        
        return documents;
    }
    
    public static EnumMap getTutorFiles(RequestContext context)throws IOException{
        ParameterMap parameters = context.getRequestParameters();
        
        EnumMap<DocumentType, byte[]> files = new EnumMap<>(DocumentType.class);
        
        files.put(DocumentType.CEDULA, 
                    parameters.getRequiredMultipartFile("tutor-cedula").getBytes());
        return files;
    }

    public static List<Tutor> extractTutorList(Map<Tutor, byte[]> map){
        List<Tutor> tutorList = new ArrayList<Tutor>();

        map.forEach((tutor, file) -> tutorList.add(tutor) );

        return tutorList;
    }

    public static Map<Tutor, byte[]> getTutorFileMap(){
        return new HashMap<Tutor, byte[]>();
    }

}
