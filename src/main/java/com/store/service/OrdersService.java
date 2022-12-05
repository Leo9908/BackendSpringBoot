package com.store.service;

import com.store.dto.OrderDTO;

public interface OrdersService {
    public Boolean receiveOrder(Long userId, OrderDTO orderDTO);
}
