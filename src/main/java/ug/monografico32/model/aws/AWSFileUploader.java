package ug.monografico32.model.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ug.monografico32.model.AmazonS3Document;
import ug.monografico32.model.CloudDocument;
import ug.monografico32.model.DocumentType;
import ug.monografico32.model.Persona;
import ug.monografico32.util.IFileUploader;

import java.io.IOException;

/**
 * Created by Jose Elias on 17/12/2016.
 */
@Service("fileUploader")
public class AWSFileUploader implements IFileUploader {

    private static final String AMAZON_S3_BUCKET = "monografico32";

    @Autowired
    private AmazonS3 s3Client;

    //document key: callerId/documentType
    public CloudDocument uploadFile(MultipartFile file, Persona p, DocumentType t) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(p.getId()).append("/").append(t.name());

        s3Client.putObject(AMAZON_S3_BUCKET, sb.toString(), file.getInputStream(), null);
        return new AmazonS3Document(sb.toString() ,t);
    }

    public void deleteDocument(AmazonS3Document document){
        s3Client.deleteObject(AMAZON_S3_BUCKET, document.getDocumentKey());
    }
}
