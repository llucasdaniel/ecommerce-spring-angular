package com.lucasd.ecommerce.config;

import com.lucasd.ecommerce.entity.Country;
import com.lucasd.ecommerce.entity.Product;
import com.lucasd.ecommerce.entity.ProductCategory;
import com.lucasd.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {


    private EntityManager entityManager;

    @Autowired
    public DataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        Disable this HTTP methods for API

        HttpMethod[] unsupporttedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        disableHttpMethods(config, unsupporttedActions, Product.class);
        disableHttpMethods(config, unsupporttedActions, ProductCategory.class);
        disableHttpMethods(config, unsupporttedActions, Country.class);
        disableHttpMethods(config, unsupporttedActions, State.class);

        //call to expose Ids
        exposeIds(config);
    }

    private void disableHttpMethods(RepositoryRestConfiguration config, HttpMethod[] unsupporttedActions, Class theClass) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsupporttedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // Get list of all entities classes from entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // create array of entity types
        List<Class> entityClasses = new ArrayList<>();

        // get Entity Types for entitites
        for (EntityType entity : entities) {
            entityClasses.add(entity.getJavaType());
        }

        //expose entity ids for the array of entity types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
