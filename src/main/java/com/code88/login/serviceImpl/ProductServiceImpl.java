package com.code88.login.serviceImpl;

import com.code88.login.dao.ProductRepository;
import com.code88.login.entity.Product;
import com.code88.login.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
     private ProductRepository productRepository;

    @Override
    public List<Product> getALlProdcts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Page<Product> findByAll(int pageNo, int pageSize, String keyword) {

        Pageable pageable= PageRequest.of(pageNo - 1,pageSize);
        if(keyword!=null){
            productRepository.findAll(pageable);
        }
        return productRepository.findAll(keyword,pageable);
    }
}
