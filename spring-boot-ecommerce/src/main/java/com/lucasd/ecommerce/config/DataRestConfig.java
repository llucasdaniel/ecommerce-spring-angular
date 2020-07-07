package com.lucasd.ecommerce.config;

import com.lucasd.ecommerce.entity.Product;
import com.lucasd.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        Disable this HTTP methods for API
        HttpMethod[] unsupporttedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions));


        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions));

    }
}
