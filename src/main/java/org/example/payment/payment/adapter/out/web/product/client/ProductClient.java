package org.example.payment.payment.adapter.out.web.product.client;

import org.example.payment.payment.domain.Product;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProductClient {

    Flux<Product> getProducts(Long cartId, List<Long> productIds);
}
