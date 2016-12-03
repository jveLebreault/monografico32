package ug.monografico32.model;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ug.monografico32.config.AppConfig;
import ug.monografico32.config.WebConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import ug.monografico32.model.aws.URLSignerWrapper;

/**
 * Created by Jose Elias on 01/12/2016.
 */

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = {AppConfig.class} )
public class AWSTest {

    @Autowired
    private AmazonS3 s3Client;
    
    @Autowired
    private URLSignerWrapper urlSigner;

    @Test
    public void KeyURLTest() throws IOException, InvalidKeySpecException {
       String signedUrl = urlSigner.getURLFor("aws.jpg");
       System.out.println(signedUrl);
       assertNotNull(signedUrl);
    }

    @Test
    public void FileUploadTest() throws URISyntaxException {

        assertNotNull(s3Client);

        InputStream is = getClass().getResourceAsStream("/something.txt");
        ObjectMetadata metadata = new ObjectMetadata();
        s3Client.putObject("monografico32", System.currentTimeMillis()+".txt",
                is, metadata);
    }
}
