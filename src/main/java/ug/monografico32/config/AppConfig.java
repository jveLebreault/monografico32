package ug.monografico32.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jose Elias on 24/10/2016.
 */
@Configuration
//@ComponentScan()
public class AppConfig {

    @Bean
    public AWSCredentialsProvider classpathPropertiesFileCredentialsProvider(){
        return new ClasspathPropertiesFileCredentialsProvider("aws/awscredentials.properties");
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider){

        return AmazonS3ClientBuilder.standard().
                withCredentials(credentialsProvider).
                withRegion(Regions.US_EAST_1 ).
                build();
    }
}
