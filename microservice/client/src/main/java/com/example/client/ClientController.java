package com.example.client;

import com.example.client.beans.*;
import com.example.client.proxies.MsCartProxy;
import com.example.client.proxies.MsOrderProxy;
import com.example.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MsProductProxy msProductProxy;

    @Autowired
    private MsCartProxy msCartProxy;

    @Autowired
    private MsOrderProxy msOrderProxy;

    CartBean cartBean;

    OrderBean orderBean;

    @Bean
    public CartBean cartBean(){
        return new CartBean();
    }

    @Bean
    public OrderBean orderBean(){
        return new OrderBean();
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
            if(cartBean.getProducts().isEmpty()){
                return "cartEmpty";
            }
            else{
                model.addAttribute("cartBean", cartBean);
                return "cart";
            }
        }
        else{
            return "cartEmpty";
        }
    }

    @RequestMapping("/addCart/{id}")
    public String addItemCart(Model model,@PathVariable Long id){
        if(cartBean==null){
            ResponseEntity<CartBean> cart = msCartProxy.createNewCart();
            cartBean = cart.getBody();
        }

       msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();

        List<ProductBean> products = msProductProxy.list();
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/orderPage")
    public String order(Model model){
        if(orderBean==null){
            ResponseEntity<OrderBean> order = msOrderProxy.createNewOrder();
            orderBean = order.getBody();
        }

        if(cartBean!=null){
            System.out.println(orderBean);
            orderBean.setCartId(cartBean.getId());
            orderBean = msOrderProxy.saveOrder(orderBean.getId(),orderBean).get();
        }

        List<OrderItemBean> products = new ArrayList<OrderItemBean>();
        if(!cartBean.getProducts().isEmpty()){
            for(int i=0;i<cartBean.getProducts().size();i++){
                ProductBean product = msProductProxy.get(cartBean.getProducts().get(i).getProductId());
                products.add(new OrderItemBean(cartBean.getProducts().get(i).getProductId(),cartBean.getProducts().get(i).getQuantity(),product.getPrice()));
            }
        }
        orderBean.calculeTotal(products);
        model.addAttribute("products",products);

        cartBean.setProducts(new ArrayList<CartItemBean>());
        msCartProxy.deleteProductsFromCart(cartBean.getId(),cartBean);

        model.addAttribute("order",orderBean);

        return "order";
    }

    @RequestMapping("/pay")
    public String pay(Model model){

        msOrderProxy.deleteOrder(orderBean.getId());
        orderBean = null;

        return "confirmation";
    }

    @GetMapping("/addOne/{id}")
    public String addOne(@PathVariable Long id, Model model){
        msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();

        model.addAttribute("cartBean",cartBean);

        return "cart";
    }

    @GetMapping("/retriveOne/{id}")
    public String retriveOne(@PathVariable Long id, Model model){
        msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,-1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();

        model.addAttribute("cartBean",cartBean);
        return "cart";
    }

}
