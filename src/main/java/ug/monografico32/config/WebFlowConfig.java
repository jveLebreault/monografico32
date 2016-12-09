package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.webflow.view.FlowAjaxThymeleafView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jose Elias on 08/12/2016.
 */
//TODO Fix the issue with the path of the template/flow
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private WebConfig webConfig;

    @Bean
    public FlowDefinitionRegistry flowRegistry(){

        return getFlowDefinitionRegistryBuilder(flowBuilderServices()).
                //setBasePath("/WEB-INF/views").
                //addFlowLocationPattern("/**/*-flow.xml").
                //addFlowLocation("/estudiante/*-flow.xml").
                //setFlowBuilderServices()
                        //builder
                                addFlowLocation("/WEB-INF/views/estudiante/agregar-flow.xml","estudiante/agregar").
                build();
    }

    @Bean
    public FlowExecutor flowExecutor(){
        return getFlowExecutorBuilder(flowRegistry()).
                build();
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter(){
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(flowExecutor());
        return handlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(){
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setFlowRegistry(flowRegistry());
        //handlerMapping.setOrder();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

    @Bean
    public AjaxThymeleafViewResolver thymeleafViewResolver(){
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setTemplateEngine(webConfig.templateEngine());
        viewResolver.setOrder(0);
        //viewResolver.
        return viewResolver;
    }


    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator(){
        ViewResolver[] resolvers = new ViewResolver[] {thymeleafViewResolver()};
        List<ViewResolver> list = Arrays.asList( resolvers );

        MvcViewFactoryCreator viewFactoryCreator = new MvcViewFactoryCreator();
        viewFactoryCreator.setViewResolvers(list);
        //viewFactoryCreator.setDefaultViewSuffix(".html");
        //viewFactoryCreator.
        //viewFactoryCreator.
        return viewFactoryCreator;
    }

    @Bean
    public FlowBuilderServices flowBuilderServices(){
        return getFlowBuilderServicesBuilder().
               setViewFactoryCreator(mvcViewFactoryCreator()).
               build();
    }


}
