package com.code88.login.dao;

import com.code88.login.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.name LIKE %?1%"+
            "or p.brand like %?1%"+"or p.category like %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);
}
