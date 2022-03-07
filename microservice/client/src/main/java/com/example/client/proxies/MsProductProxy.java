package com.example.client.proxies;

import com.example.client.beans.ProductBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-client", url="localhost:8091")
public interface MsProductProxy {

    @GetMapping("/products")
    public List<ProductBean> list();

    @GetMapping("/product/{id}")
    public ProductBean get(@PathVariable Long id);

}
