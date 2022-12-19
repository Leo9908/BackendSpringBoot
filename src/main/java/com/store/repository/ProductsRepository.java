package com.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.store.entitys.Product;
import com.store.entitys.User;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
 
    public List<Product> findByDeleted(Boolean value);

    public List<Product> findByOnSale(Boolean value);

    @Query(value = "SELECT p.* FROM [dbo].[products] as p WHERE p.on_sale=1 AND p.name LIKE %?1% OR p.description LIKE %?1%", nativeQuery = true)
    public List<Product> findAll(String keyWord);
    
    @Query(value = "SELECT p.* FROM products p JOIN ratings pr ON p.id = pr.product_id WHERE p.on_sale=1 AND pr.user_id = ?1 AND pr.stars = 5", nativeQuery = true)
    public List<Product> findAll(User user);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products_order SET products_order.amount = :amount WHERE products_order.order_id = :order_id AND products_order.product_id = :product_id", nativeQuery = true)
    public void insertAmount(@Param("amount") Integer amount, @Param("order_id") Long orderId,
            @Param("product_id") Long productId);

    @Query(value = "WITH RPA AS " +
            "(" +
            "SELECT product_id, SUM(amount) AS sa FROM products_order GROUP BY product_id" +
            ")" +
            "SELECT pr.* FROM products pr JOIN RPA rpa ON pr.id = rpa.product_id WHERE rpa.sa >= (SELECT AVG(rpa.sa) FROM RPA)", nativeQuery = true)
    public List<Product> findAllMostSold();

    public List<Product> findByImgNameAndDeleted(String name, Boolean deleted);

}