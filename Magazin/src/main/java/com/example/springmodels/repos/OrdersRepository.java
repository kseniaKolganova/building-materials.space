package com.example.springmodels.repos;

import com.example.springmodels.models.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

}
