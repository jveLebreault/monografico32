/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ug.monografico32.model.aws;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/*
This class will be provded as a singleton through the spring Autowiring facility
*/
public class URLSignerWrapper {
    
    private String distributionDomain;
    private String keyPairID;
    private String privateKeyFilePath;
    private File privateKeyFile;
    
    public URLSignerWrapper(String domain, String keyPair, 
                               String privateKeyPath) throws URISyntaxException{
        
        this.distributionDomain = domain;
        this.keyPairID = keyPair;
        this.privateKeyFilePath = privateKeyPath;
        privateKeyFile = new File( getClass().
                getResource(privateKeyFilePath).toURI());        
    }
    
    public String getURLFor(String objectKey) 
                                    throws InvalidKeySpecException, IOException{
        
        return CloudFrontUrlSigner.getSignedURLWithCannedPolicy( 
                SignerUtils.Protocol.https,
                this.distributionDomain,
                this.privateKeyFile,
                objectKey,
                this.keyPairID,
                new Date(System.currentTimeMillis() + 60 * 1000) );
    }
    
}
