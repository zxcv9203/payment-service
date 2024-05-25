package org.example.payment.payment.application.port.out;

import org.example.payment.payment.domain.Product;
import reactor.core.publisher.Flux;

import java.util.List;

public interface LoadProductPort {

    Flux<Product> getProduct(Long cartId, List<Long> productIds);

}
