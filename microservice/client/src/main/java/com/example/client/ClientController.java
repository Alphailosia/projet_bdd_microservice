package com.example.client;

import com.example.client.beans.CartBean;
import com.example.client.beans.CartItemBean;
import com.example.client.beans.ProductBean;
import com.example.client.proxies.MsCartProxy;
import com.example.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
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

    CartBean cartBean;

    @Bean
    public CartBean cartBean(){
        return new CartBean();
    }

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

    @RequestMapping("/cart")
    public String cart(Model model){
        if(cartBean!=null){
            model.addAttribute("cartBean", cartBean);
            return "cart";
        }
        else{
            List<ProductBean> products = msProductProxy.list();
            model.addAttribute("products", products);
            return "index";
        }
    }

    @RequestMapping({"/addCart/{id}"})
    public String addItemCart(Model model,@PathVariable Long id){
        if(cartBean==null){
            ResponseEntity<CartBean> cart = msCartProxy.createNewCart();
            cartBean = cart.getBody();
        }

       msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();
        System.out.println(cartBean);
        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        return "index";
    }

}
