package com.example.client;

import com.example.client.beans.*;
import com.example.client.proxies.MsAuthProxy;
import com.example.client.proxies.MsCartProxy;
import com.example.client.proxies.MsOrderProxy;
import com.example.client.proxies.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private MsAuthProxy msAuthProxy;

    CartBean cartBean;

    OrderBean orderBean;

    UserBean userBean;

    @Bean
    public CartBean cartBean(){
        return new CartBean();
    }

    @Bean
    public OrderBean orderBean(){
        return new OrderBean();
    }

    @Bean
    public UserBean userBean(){ return new UserBean(); }

    @RequestMapping("/")
    public String index(Model model) {
        if(userBean==null){
            userBean = new UserBean("","");
        }
        model.addAttribute("userbean",userBean);
        boolean res = msAuthProxy.authenticate(userBean);
        if(res){
            List<ProductBean> products = msProductProxy.list();
            model.addAttribute("products", products);
            return "index";
        }
        else{
            return "authpage";
        }
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
        if(cartBean != null){
            if(!cartBean.getProducts().isEmpty()){
                for(int i=0;i<cartBean.getProducts().size();i++){
                    ProductBean product = msProductProxy.get(cartBean.getProducts().get(i).getProductId());
                    products.add(new OrderItemBean(cartBean.getProducts().get(i).getProductId(),cartBean.getProducts().get(i).getQuantity(),product.getPrice()));
                }
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

    @RequestMapping("/addOne/{id}")
    public String addOne(@PathVariable Long id, Model model){
        msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();

        model.addAttribute("cartBean",cartBean);

        return "cart";
    }

    @RequestMapping("/retriveOne/{id}")
    public String retriveOne(@PathVariable Long id, Model model){
        msCartProxy.addProductToCart(cartBean.getId(),new CartItemBean(id,-1));

        cartBean = msCartProxy.getCart(cartBean.getId()).get();

        model.addAttribute("cartBean",cartBean);

        if(cartBean.getProducts().isEmpty()){
            return "cartEmpty";
        }
        return "cart";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String auth(@ModelAttribute("userbean") UserBean userBean, Model model){
        boolean res = msAuthProxy.authenticate(userBean);

        if(res){
            this.userBean = new UserBean("","");
            this.userBean.setName(userBean.getName());
            this.userBean.setPassword(userBean.getPassword());
            List<ProductBean> products = msProductProxy.list();
            model.addAttribute("products", products);
            return "index";
        }
        else{
            return "wrongAuth";
        }
    }

    @RequestMapping("/deconnexion")
    public String deconnexion(Model model){
        this.userBean.setPassword("");
        this.userBean.setName("");

        model.addAttribute("userbean",userBean);
        return "authpage";
    }

}
