package com.authguard.authguard_user_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.authguard.authguard_user_service.Interceptors.AuthInterceptors;
/*
 * A web Incterceptor configuration to perform 
 * prepossing of reqst and post processing of respon.
 * <p>
 * @implement {@link webMvcConfiguerer} to add global  interceptors 
 * </p>
 * 
 */
@Configuration
public class InterceptorConfig implements   WebMvcConfigurer {
    
    /*
     * AuthInteceptor for perform authentication of http request.
     * 
     */
    @Autowired
    private AuthInterceptors authInterceptors;

    /*
     * Add interceptors to executre before and afte
     * 
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptors);
    }
}
