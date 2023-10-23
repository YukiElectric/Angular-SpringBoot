package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamException;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new DataNotFoundException(
                "Cannot find category with id :"+productDTO.getCategoryId()));
        Product product = Product.builder()
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .thumbnail(" ")
            .description(productDTO.getDescription())
            .category(existingCategory)
            .build();
        return productRepository.save(product);
    }
    
    @Override
    public Product getProductById(long id) throws DataNotFoundException {
        return productRepository.findById(id)
            .orElseThrow(()-> new DataNotFoundException("Cannot find product with id ="+id));
    }
    
    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        
        return productRepository.findAll(pageRequest).map(product -> {
            ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());
            return productResponse;
        });
    }
    
    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException{
        Product existingProduct = getProductById(id);
        if(existingProduct != null) {
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                    "Cannot find category with id :"+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }
    
    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }
    
    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    
    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO)
        throws DataNotFoundException, InvalidParamException {
        Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new DataNotFoundException(
                "Cannot find product with id :"+productImageDTO.getProductId()));
        ProductImage productImage = ProductImage.builder()
            .product(existingProduct)
            .imageUrl(productImageDTO.getImageUrl())
            .build();
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= 5) {
            throw new InvalidParamException("Number of images must be <= 5");
        }
        return productImageRepository.save(productImage);
    }
}
