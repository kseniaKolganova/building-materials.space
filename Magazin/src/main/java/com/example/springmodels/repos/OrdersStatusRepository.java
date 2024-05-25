package com.example.springmodels.repos;

import com.example.springmodels.models.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrdersStatusRepository extends CrudRepository<OrderStatus, Long> {
    OrderStatus findByName(String name);
}
