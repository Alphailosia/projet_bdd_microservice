package com.example.client.proxies;

import com.example.client.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-auth",url = "localhost:8094")
public interface MsAuthProxy {

    @PostMapping("/auth")
    public boolean authenticate(@RequestBody UserBean userBean);

}
