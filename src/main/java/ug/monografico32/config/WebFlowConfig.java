package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.persistence.JpaFlowExecutionListener;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.webflow.view.FlowAjaxThymeleafView;

import javax.persistence.EntityManagerFactory;
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

    @Bean
    public FlowDefinitionRegistry flowRegistry(){

        return getFlowDefinitionRegistryBuilder(flowBuilderServices()).
               setBasePath("/WEB-INF/views").
               addFlowLocation("/estudiante/agregar-flow.xml", "estudiante/agregar").
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
        handlerMapping.setOrder(0);
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
               setValidator( validator()).
               build();
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
