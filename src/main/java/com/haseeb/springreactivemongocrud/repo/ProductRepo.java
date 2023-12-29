package com.haseeb.springreactivemongocrud.repo;

import com.haseeb.springreactivemongocrud.dto.ProductDto;
import com.haseeb.springreactivemongocrud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepo extends ReactiveMongoRepository<Product,String> {
    Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
