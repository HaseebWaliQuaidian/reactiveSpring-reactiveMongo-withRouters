package com.haseeb.springreactivemongocrud.router;

import com.haseeb.springreactivemongocrud.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    @Autowired
    private ProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse> productRoutes(){
        return RouterFunctions.route()
                .GET("/router/products",productHandler::getProducts)
                .GET("/router/products/{id}",productHandler::getProductById)
                .POST("/router/products",productHandler::saveProduct)
                .PUT("/router/products/{id}",productHandler::updateProduct)
                .DELETE("/router/products/{id}", productHandler::deleteProduct)
                .build();
    }
}
