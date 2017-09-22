package ug.monografico32.util;

import org.springframework.web.multipart.MultipartFile;
import ug.monografico32.model.CloudDocument;
import ug.monografico32.model.DocumentType;
import ug.monografico32.model.Persona;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

public interface IFileUploader {

    CloudDocument uploadFile(InputStream file, Persona p, DocumentType t) throws IOException;
}
