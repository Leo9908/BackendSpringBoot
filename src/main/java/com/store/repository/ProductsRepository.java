package com.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.entitys.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    public List<Product> findByOnSale(Boolean value);
}