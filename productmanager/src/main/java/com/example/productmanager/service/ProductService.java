package com.example.productmanager.service;

import com.example.productmanager.dto.ProductResponse;
import com.example.productmanager.entity.Product;
import com.example.productmanager.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.productmanager.dto.ProductRequest;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();

        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        Product savedProduct = productRepository.save(product);
        ProductResponse response = new ProductResponse();

        response.setId(savedProduct.getId());
        response.setProductName(savedProduct.getProductName());
        response.setPrice(savedProduct.getPrice());
        response.setStock(savedProduct.getStock());
        response.setActive(savedProduct.isActive());

        return response;
    }
    public List<ProductResponse> getAllProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);

        return products.stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();

                    response.setId(product.getId());
                    response.setProductName(product.getProductName());
                    response.setPrice(product.getPrice());
                    response.setStock(product.getStock());
                    response.setActive(product.isActive());

                    return response;
                })
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,"Product not found"
                        ));
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setActive(product.isActive());

        return response;
    }
    public Product updateProductById(Long id, ProductRequest productRequest) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStock(productRequest.getStock());

        return productRepository.save(existingProduct);
    }
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));

        productRepository.delete(existingProduct);
    }

    public List<ProductResponse> searchProducts(String name) {

        List<Product> products =
                productRepository.findByProductNameContainingIgnoreCase(name);

        return products.stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();

                    response.setId(product.getId());
                    response.setProductName(product.getProductName());
                    response.setPrice(product.getPrice());
                    response.setStock(product.getStock());
                    response.setActive(product.isActive());

                    return response;
                })
                .toList();
    }
    public List<ProductResponse> filterProductsByActive(boolean active) {

        List<Product> products = productRepository.findByActive(active);

        return products.stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();

                    response.setId(product.getId());
                    response.setProductName(product.getProductName());
                    response.setPrice(product.getPrice());
                    response.setStock(product.getStock());
                    response.setActive(product.isActive());

                    return response;
                })
                .toList();
    }
    }

