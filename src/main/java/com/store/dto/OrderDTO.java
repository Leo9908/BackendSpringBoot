package com.store.dto;

import java.util.Collection;
import java.util.Map;

import io.micrometer.core.lang.NonNull;

public class OrderDTO {


    private Long id;
    @NonNull
    private Collection<Long> addresses;
    @NonNull
    private Map<Long, Integer> products;
    private String secondPhone;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Collection<Long> getAddresses() {
        return addresses;
    }
    public void setAddresses(Collection<Long> addresses) {
        this.addresses = addresses;
    }
    public Map<Long, Integer> getProducts() {
        return products;
    }
    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }
    public String getSecondPhone() {
        return secondPhone;
    }
    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }



}
