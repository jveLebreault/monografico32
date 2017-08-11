package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;
import org.springframework.webflow.mvc.servlet.FlowHandler;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.persistence.JpaFlowExecutionListener;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.webflow.view.FlowAjaxThymeleafView;
import ug.monografico32.model.Curso;
import ug.monografico32.util.converter.StringToAsignaturaConverter;
import ug.monografico32.util.converter.StringToCursoConverter;
import ug.monografico32.util.converter.StringToDocenteConverter;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.TransactionManager;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jose Elias on 08/12/2016.
 */
@Configuration
@ComponentScan
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private WebConfig webConfig;

    @Autowired
    private StringToCursoConverter stringToCursoConverter;

    @Autowired
    private StringToAsignaturaConverter stringToAsignaturaConverter;

    @Autowired
    private StringToDocenteConverter stringToDocenteConverter;

    @Bean
    public FlowDefinitionRegistry flowRegistry(){

        return getFlowDefinitionRegistryBuilder(flowBuilderServices()).
               setBasePath("/WEB-INF/views").
               addFlowLocation("/estudiante/agregar-flow.xml", "estudiante/agregar").
               addFlowLocation("/horario/crear-flow.xml","horario/agregar").
               build();
    }

    @Bean
    public FlowExecutor flowExecutor(JpaFlowExecutionListener flowExecutionListener){
        return getFlowExecutorBuilder(flowRegistry()).
               addFlowExecutionListener(flowExecutionListener).
               setMaxFlowExecutions(5).
               setMaxFlowExecutionSnapshots(10).
               build();
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter(JpaFlowExecutionListener listener){
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(flowExecutor(listener));
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(){
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setFlowRegistry(flowRegistry());
        handlerMapping.setOrder(-1);
        return handlerMapping;
    }

    @Bean
    public AjaxThymeleafViewResolver thymeleafViewResolver(){
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setTemplateEngine(webConfig.templateEngine());
        viewResolver.setOrder(0);
        return viewResolver;
    }


    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator(){
        ViewResolver[] resolvers = new ViewResolver[] {thymeleafViewResolver()};
        List<ViewResolver> list = Arrays.asList( resolvers );

        MvcViewFactoryCreator viewFactoryCreator = new MvcViewFactoryCreator();
        viewFactoryCreator.setViewResolvers(list);
        return viewFactoryCreator;
    }

    @Bean
    public FlowBuilderServices flowBuilderServices(){
        return getFlowBuilderServicesBuilder().
               setViewFactoryCreator(mvcViewFactoryCreator()).
               setConversionService(conversionService()).
               setValidator( validator()).
               build();
    }

    @Bean
    public ConversionService conversionService(){
        DefaultConversionService conversionService =
                new DefaultConversionService( webConfig.conversionService());
        return conversionService;
    }

    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public JpaFlowExecutionListener jpaFlowExecutionListener(EntityManagerFactory emf,
                                                             PlatformTransactionManager tx){
        return new JpaFlowExecutionListener(emf, tx);
    }

}
