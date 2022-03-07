package com.example.client;

import com.example.client.beans.CartBean;
import com.example.client.beans.CartItemBean;
import com.example.client.beans.ProductBean;
import com.example.client.proxies.MsCartProxy;
import com.example.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @RequestMapping("/")
    public String index(Model model) {
        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/product-detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        ProductBean product = msProductProxy.get(id);
        model.addAttribute("product", product);
        return "detail";
    }
}
