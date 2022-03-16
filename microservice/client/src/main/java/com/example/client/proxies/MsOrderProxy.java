package com.example.client.proxies;

import com.example.client.beans.OrderBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "ms-order",url="localhost:8093")
public interface MsOrderProxy {

    @PostMapping("/order")
    public ResponseEntity<OrderBean> createNewOrder();

    @GetMapping("/order/{id}")
    public Optional<OrderBean> getOrder(@PathVariable Long id);

    @PostMapping("/order/{id}")
    public Optional<OrderBean> saveOrder(@PathVariable Long id,@RequestBody OrderBean orderBean);

}
