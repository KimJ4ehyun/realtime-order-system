package com.example.productservice.service;

import com.example.productservice.dto.ProductOrderRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsWithStockValidation(List<ProductOrderRequest> requests) {
        List<Long> productIds = requests.stream()
                .map(ProductOrderRequest::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        return requests.stream()
                .map(request -> {
                    Product product = productMap.get(request.getProductId());

                    boolean isValid = product != null && product.isStockAvailable(request.getQuantity());

                    return ProductResponse.of(
                            request.getProductId(),
                            request.getQuantity(),
                            isValid,
                            product != null ? product.getAmount() : null
                    );
                })
                .toList();
    }
}
