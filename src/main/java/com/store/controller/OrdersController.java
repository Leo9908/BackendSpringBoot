package com.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.dto.OrderDTO;
import com.store.service.OrdersService;

@Controller
@RequestMapping("/api/{userId}/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @PostMapping
    public ResponseEntity<Boolean> receiveOrder(@PathVariable(name = "userId") Long userId,
            @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(service.receiveOrder(userId, orderDTO), HttpStatus.CREATED);
    }

}
