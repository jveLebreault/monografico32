package ug.monografico32.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.webflow.execution.RequestContext;
import ug.monografico32.model.DocumentType;

import java.util.EnumMap;

/**
 * Created by Jose Elias on 31/12/2016.
 */
//TODO: get files from context and save in map to eventually proccess these files and the end of the flow
public class FileMapMaker {

    public EnumMap getFileMap(RequestContext context){
        EnumMap<DocumentType, MultipartFile> documents = new EnumMap<>(DocumentType.class);

        return documents;
    }
}
