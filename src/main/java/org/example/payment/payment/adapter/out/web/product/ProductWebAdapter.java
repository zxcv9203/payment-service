package org.example.payment.payment.adapter.out.web.product;

import lombok.RequiredArgsConstructor;
import org.example.payment.common.WebAdapter;
import org.example.payment.payment.adapter.out.web.product.client.ProductClient;
import org.example.payment.payment.application.port.out.LoadProductPort;
import org.example.payment.payment.domain.Product;
import reactor.core.publisher.Flux;

import java.util.List;

@WebAdapter
@RequiredArgsConstructor
public class ProductWebAdapter implements LoadProductPort {
    private final ProductClient productClient;
    @Override
    public Flux<Product> getProduct(Long cartId, List<Long> productIds) {
        return productClient.getProducts(cartId, productIds);
    }
}
