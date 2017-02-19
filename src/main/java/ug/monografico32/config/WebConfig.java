package ug.monografico32.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import ug.monografico32.dao.AsignaturaRepository;
import ug.monografico32.dao.DocenteRepository;
import ug.monografico32.util.converter.StringToAsignaturaConverter;
import ug.monografico32.util.converter.StringToDocenteConverter;

/**
 * Created by Jose Elias on 24/10/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ug.monografico32.controller", "ug.monografico32.util.converter"}/*,
        basePackageClasses = WebFlowConfig.class*/)
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    
    @Autowired
    private DocenteRepository docenteRepository;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**").
                addResourceLocations("/resources/").
                addResourceLocations("classpath:/META-INF/web-resources/").
                setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).
                cachePublic());
    }

    @Bean
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource= new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        //messageSource.
        return messageSource;
    }

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver ();
        viewResolver.setTemplateEngine( templateEngine() );
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver( templateResolver() );
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        return templateResolver;
    }
 
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter( new StringToDocenteConverter( docenteRepository) );
        registry.addConverter( new StringToAsignaturaConverter( asignaturaRepository) );
    }
    
    /*@Bean
    public LongToDocente aa(DocenteRepository repository){
        return new LongToDocente(repository);
    }*/
    
    /*@Bean( name = "conversionService")
    public ConversionServiceFactoryBean getConversionService(DocenteRepository repository){
        ConversionServiceFactoryBean conversionService = new ConversionServiceFactoryBean();
        
        LongToDocente longToDocenteConverter = new LongToDocente(repository);
        
        Set<Converter> converters = new HashSet<>();
        converters.add(longToDocenteConverter);
        
        conversionService.setConverters(converters);
        return conversionService;
    }*/
}
