package ug.monografico32.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Registration;
import javax.servlet.ServletRegistration;

/**
 * Created by Jose Elias on 24/10/2016.
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{ WebConfig.class, WebFlowConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration){
        registration.setMultipartConfig(
                new MultipartConfigElement( System.getProperty("user.home"),
                                            2097152, 10485760, 2097152));
    }
}
