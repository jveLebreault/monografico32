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

/**
 * Created by Jose Elias on 01/12/2016.
 */

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = {AppConfig.class} )
public class AWSTest {

    @Autowired
    private AmazonS3 s3Client;

    @Test
    public void KeyURLTest() throws IOException, InvalidKeySpecException {
        // the DNS name of your CloudFront distribution, or a registered alias
        String distributionDomainName = "d3tel6qtlf2frl.cloudfront.net";
// the private key you created in the AWS Management Console
        File cloudFrontPrivateKeyFile = new File( "C:\\Users\\Jose Elias\\IdeaProjects\\monografico32\\src\\main\\resources\\aws\\spk-APKAI4TDCZZ35A5ORW3A.der" );
// the unique ID assigned to your CloudFront key pair in the console
        String cloudFrontKeyPairId = "APKAI4TDCZZ35A5ORW3A";
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 1000);

        String signedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                SignerUtils.Protocol.https,
                distributionDomainName,
                cloudFrontPrivateKeyFile,
                "aws.jpg", // the resource path to our content
                cloudFrontKeyPairId,
                expirationDate);
        System.out.println(signedUrl);
    }

    @Test
    public void FileUploadTest() throws URISyntaxException {

        assertNotNull(s3Client);

        InputStream is = getClass().getResourceAsStream("/something.txt");
        ObjectMetadata metadata = new ObjectMetadata();
        s3Client.putObject("monografico32", System.currentTimeMillis()+".txt",is, metadata);
    }
}
