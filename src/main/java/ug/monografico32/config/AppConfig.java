package ug.monografico32.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ug.monografico32.model.aws.URLSignerWrapper;

@Configuration
@Import({SecurityConfig.class, PersistenceConfig.class})
@PropertySource("classpath:/aws/cloudfront.properties")
@ComponentScan( basePackages = {"ug.monografico32.model", "ug.monografico32.service"})
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
