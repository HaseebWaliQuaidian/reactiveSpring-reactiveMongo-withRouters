package com.haseeb.springreactivemongocrud.handler;

import com.haseeb.springreactivemongocrud.dto.ProductDto;
import com.haseeb.springreactivemongocrud.entity.Product;
import com.haseeb.springreactivemongocrud.service.ProductService;
import com.haseeb.springreactivemongocrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductHandler {
    @Autowired
    private ProductService productService;

    public Mono<ServerResponse> getProducts(ServerRequest req) {
        Flux<ProductDto> productsDto = productService.getProducts();
        return ServerResponse.ok().body(productsDto,ProductDto.class);
    }
    public Mono<ServerResponse> saveProduct(ServerRequest req){
        Mono<ProductDto> productDtoMono = productService.saveProduct(req.bodyToMono(ProductDto.class));
        return ServerResponse.ok().body(productDtoMono,ProductDto.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest req){
        final String id = req.pathVariable("id");
        return ServerResponse.ok().body(productService.getProduct(id),ProductDto.class);
    }

    public Mono<ServerResponse> updateProduct(ServerRequest req){
        final String id = req.pathVariable("id");
        return ServerResponse.ok().body(
                productService.updateProduct(req.bodyToMono(ProductDto.class),id),
                ProductDto.class
        );
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest req) {
        final String id = req.pathVariable("id");
        return ServerResponse.ok().body(productService.deleteProduct(id),String.class);
    }
}
