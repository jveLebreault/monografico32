package ug.monografico32.model;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ug.monografico32.config.AppConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.spec.InvalidKeySpecException;
import static org.junit.Assert.assertNotNull;

import ug.monografico32.config.TestConfig;
import ug.monografico32.model.aws.URLSignerWrapper;

/**
 * Created by Jose Elias on 01/12/2016.
 */

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = {AppConfig.class, TestConfig.class} )
@ActiveProfiles("test")
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
