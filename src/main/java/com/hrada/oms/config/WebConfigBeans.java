package com.hrada.oms.config;

import com.hrada.oms.util.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * Created by shin on 2018/4/11.
 */
@Configuration
public class WebConfigBeans {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void initEditableValidation(){
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if(configurableWebBindingInitializer.getConversionService()!=null){
            GenericConversionService service = (GenericConversionService) configurableWebBindingInitializer.getConversionService();
            service.addConverter(new StringToDateConverter());
        }
    }
}
