package ug.monografico32.model.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import eu.medsea.mimeutil.MimeUtil;
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ug.monografico32.model.*;
import ug.monografico32.util.IFileUploader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service("fileUploader")
public class AWSFileUploader implements IFileUploader {

    private static final String AMAZON_S3_BUCKET = "monografico32";

    @Autowired
    private AmazonS3 s3Client;
    
    public AWSFileUploader(AmazonS3 s3Client){
        this.s3Client = s3Client;
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
    }

    //document key: callerId/documentType
    @Override
    public AmazonS3Document uploadFile(InputStream file, Persona p, DocumentType t)
                                                            throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        String mimeType = MimeUtil.getMimeTypes(file).toArray()[0].toString();
        System.out.println(mimeType);
        metadata.setContentType(mimeType);
        StringBuilder sb = new StringBuilder();
        sb.append(p.getId()).append("/").append(t.name());

        s3Client.putObject(AMAZON_S3_BUCKET, sb.toString(), file, metadata);
        return new AmazonS3Document(sb.toString() ,t);
    }

    public void deleteFile(AmazonS3Document document){
        s3Client.deleteObject(AMAZON_S3_BUCKET, document.getDocumentKey());
    }
    
    public Set<AmazonS3Document> uploadFilesMap(EnumMap<DocumentType, byte[]>
                                             files, Persona p){
        Set<AmazonS3Document> documents = new HashSet<>();
        
        files.entrySet().stream().forEach(e -> {
            try {
                AmazonS3Document doc = uploadFile(
                        new ByteArrayInputStream( e.getValue() ), p, e.getKey()); 
                documents.add( doc );
            } catch (IOException ex) {
                Logger.getLogger(AWSFileUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return documents;
    }
    
    public AmazonS3Document uploadSingleFile(EnumMap<DocumentType, byte[]>
                                          files, Persona p, DocumentType key) throws IOException{
        ByteArrayInputStream is = new ByteArrayInputStream(files.get(key));
        return uploadFile( is, p, key);
    }

    public void uploadTutorFiles(List<Tutor> tutores, Map<Tutor, byte[]> map) throws IOException {

        for(Tutor t : tutores ){
            ByteArrayInputStream is = new ByteArrayInputStream( map.get(t) );
            t.setCedula( uploadFile(is, t, DocumentType.CEDULA) );
        }
    }
}
