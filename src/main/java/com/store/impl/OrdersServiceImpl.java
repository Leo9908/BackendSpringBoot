package com.store.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.OrderDTO;
import com.store.entitys.Address;
import com.store.entitys.Order;
import com.store.entitys.Product;
import com.store.entitys.User;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repository.AddressRepository;
import com.store.repository.OrdersRepository;
import com.store.repository.ProductsRepository;
import com.store.repository.UsersRepository;
import com.store.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OrdersRepository repository;

    @Autowired
    private UsersRepository repository2;

    @Autowired
    private AddressRepository repository3;

    @Autowired
    private ProductsRepository repository4;

    @Override
    public Boolean receiveOrder(Long userId, OrderDTO orderDTO) {
        Collection<Order> orders = createOrders(userId, orderDTO);
        Collection<Order> newOrders = repository.saveAll(orders);
        newOrders.forEach((order) -> {
            orderDTO.getProducts().forEach((key, value) -> {
                repository4.insertAmount(value, order.getId(), key);
            });
        });
        return true;
    }

    private Collection<Order> createOrders(Long userId, OrderDTO orderDTO) {
        Collection<Order> orders = new HashSet<>();
        User user = repository2.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Collection<Product> products = new ArrayList<>();
        orderDTO.getProducts().forEach((key, value) -> {
            Product product = repository4.findById(key)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", key));
            products.add(product);
        });
        orderDTO.getAddresses().forEach((addr) -> {
            Address address = repository3.findById(addr)
                    .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addr));
            /**
             * Aqí debo validar cada dirección para ver si puede existir o no.
             * Por ahora voy a tomarlas todas como validas
             */
            Order newOrder = new Order(1, address, user);
            newOrder.setProducts(products);
            orders.add(newOrder);
        });
        return orders;
    }

    private OrderDTO mapOrderDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }

    private Order mapOrderEntity(OrderDTO orderDTO) {
        return mapper.map(orderDTO, Order.class);
    }

}
