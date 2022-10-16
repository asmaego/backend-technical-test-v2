package com.tui.proof.repository;

import com.tui.proof.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    Optional<Order> findByNumber(UUID number);

    @Query(value = "select o FROM orders o INNER JOIN clients c ON o.client.id=c.id where c.firstName like %:keyword% or c.lastName like %:keyword% or c.email like %:keyword% or c.telephone like %:keyword%")
    List<Order> findByKeyword(@Param("keyword") String keyword);
}
