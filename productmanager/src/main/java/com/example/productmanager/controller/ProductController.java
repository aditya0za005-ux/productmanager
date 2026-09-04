package com.example.productmanager.controller;

import com.example.productmanager.dto.ProductRequest;
import com.example.productmanager.dto.ProductResponse;
import com.example.productmanager.entity.Product;
import com.example.productmanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
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
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
    }
}
