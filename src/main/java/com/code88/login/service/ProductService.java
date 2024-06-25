package com.code88.login.service;

import com.code88.login.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getALlProdcts();

    void saveProduct(Product product);

    Optional<Product> getById(Long id);

    void delete(Product product);

    Page<Product> findByAll(int pageNo, int pageSize, String keyword);

}
