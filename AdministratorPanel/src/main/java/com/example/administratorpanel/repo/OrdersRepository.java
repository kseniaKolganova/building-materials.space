package com.example.administratorpanel.repo;

import com.example.administratorpanel.model.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

    List<Orders> findByOrderDateBetween(Date startDate, Date endDate);
}
