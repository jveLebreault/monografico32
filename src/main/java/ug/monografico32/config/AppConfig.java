package ug.monografico32.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
//import ug.monografico32.controller.EstudianteController;
import ug.monografico32.model.aws.URLSignerWrapper;

/**
 * Created by Jose Elias on 24/10/2016.
 */
@Configuration
@PropertySource("classpath:/aws/cloudfront.properties")
@ComponentScan( basePackages = {"ug.monografico32.model"})
public class AppConfig {
    
    @Autowired
    private Environment env;
    
    @Bean
    public URLSignerWrapper urlSignerWrapper() throws URISyntaxException{
        return new URLSignerWrapper(env.getProperty("distributionDomain"),
                                    env.getProperty("keypairId"),
                                    env.getProperty("privateKeyFilePath"));
    }

    @Bean
    public AWSCredentialsProvider classpathPropertiesFileCredentialsProvider(){
        return new ClasspathPropertiesFileCredentialsProvider("aws/s3credentials.properties");
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider){

        return AmazonS3ClientBuilder.standard().
                withCredentials(credentialsProvider).
                withRegion(Regions.US_EAST_1 ).
                build();
    }
}
