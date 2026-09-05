package com.example.productmanager.controller;

import com.example.productmanager.dto.ProductRequest;
import com.example.productmanager.dto.ProductResponse;
import com.example.productmanager.entity.Product;
import com.example.productmanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse response = productService.createProduct(productRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public List<ProductResponse> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductByid(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }
    @GetMapping("/filter")
    public List<ProductResponse> filterProducts(@RequestParam boolean active) {
        return productService.filterProductsByActive(active);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest){
        return productService.updateProductById(id,productRequest);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product deleted successfully");

        return ResponseEntity.ok(response);
    }
}
