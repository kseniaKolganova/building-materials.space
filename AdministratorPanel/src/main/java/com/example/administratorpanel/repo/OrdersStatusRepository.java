package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrdersStatusRepository extends CrudRepository<OrderStatus, Long> {
    OrderStatus findByName(String name);
}
