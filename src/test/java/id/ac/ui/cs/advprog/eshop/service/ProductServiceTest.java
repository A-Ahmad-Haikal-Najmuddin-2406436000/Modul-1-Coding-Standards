package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreate() {
        Product product = new Product();
        productService.create(product);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        when(productRepository.findById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"))).thenReturn(product);

        Product result = productService.findById(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));

        assertEquals(product, result);
    }

    @Test
    void testEdit() {
        Product product = new Product();
        productService.edit(product);
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testDelete() {
        productService.delete(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        verify(productRepository, times(1)).delete(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }
}