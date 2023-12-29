package com.haseeb.springreactivemongocrud.service;

import com.haseeb.springreactivemongocrud.dto.ProductDto;
import com.haseeb.springreactivemongocrud.entity.Product;
import com.haseeb.springreactivemongocrud.repo.ProductRepo;
import com.haseeb.springreactivemongocrud.utils.AppUtils;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Flux<ProductDto> getProducts(){
        return productRepo.findAll().map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return productRepo.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInPriceRange(double minPrice, double maxPrice){
        return productRepo.findByPriceBetween(Range.closed(minPrice,maxPrice)).map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(productRepo::insert)
                .map(AppUtils::entityToDto);

    }
    public  Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono,String id){
        return productRepo.findById(id)
                .flatMap( dbResult -> productDtoMono.map(productDto -> {
                    dbResult.setName(productDto.getName());
                    dbResult.setQty(productDto.getQty());
                    dbResult.setPrice(productDto.getPrice());
                    return dbResult;
                }))
                .flatMap(productRepo::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return productRepo.deleteById(id);
    }

}
