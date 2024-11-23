package com.shirtstore.service;

import com.shirtstore.controller.frontend.cart.ShoppingCart;
import com.shirtstore.dao.CartDAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Cart;
import com.shirtstore.entity.CartDTO;
import com.shirtstore.entity.Shirt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {
    private HttpServletRequest request;
    private CartDAO cartDAO;
    private ShirtDAO shirtDAO;

    public CartService(HttpServletRequest request) {
        this.request = request;
        this.cartDAO = new CartDAO();
        this.shirtDAO = new ShirtDAO();
    }

    public void saveCart(int customer_id){
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            session.setAttribute("cart", shoppingCart);
        }

        shoppingCart = (ShoppingCart) session.getAttribute("cart");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customer_id);
        List<Cart> listCarts = cartDAO.findWithNamedQuery("Cart.findAllByCustomer_id", parameters);

        for (CartDTO cartDTO : shoppingCart.getCarts()){
            for(Cart cart : listCarts){
                if(cart.getProduct_id() == cartDTO.getShirt().getShirtId()){
                    cart.setQuantity(cartDTO.getQuantity());
                    cart.setSize(cartDTO.getSize());
                    cartDAO.update(cart);
                    break;
                }
            }
        }

        for (CartDTO cartDTO : shoppingCart.getCarts()){
            boolean found = false;
            for(Cart cart : listCarts){
                if(cart.getProduct_id() == cartDTO.getShirt().getShirtId()){
                    found = true;
                }
            }

            if(!found){
                cartDAO.create(new Cart(customer_id, cartDTO.getShirt().getShirtId(), cartDTO.getQuantity(), cartDTO.getSize()));
            }
        }

        listCarts = cartDAO.findWithNamedQuery("Cart.findAllByCustomer_id", parameters);

        List<CartDTO> listCartDTOs = new ArrayList<>();
        for(Cart cart : listCarts){
            Shirt shirt = shirtDAO.get(cart.getProduct_id());
            listCartDTOs.add(new CartDTO(customer_id, shirt, cart.getQuantity(), cart.getSize()));
        }

        shoppingCart.setCarts(listCartDTOs);
        session.setAttribute("cart", shoppingCart);
    }

    public void updateCart(int customerId) {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
        shoppingCart.deleteCart(customerId);

        for(CartDTO cartDTO : shoppingCart.getCarts()){
            cartDAO.create(new Cart(customerId,cartDTO.getShirt().getShirtId(),cartDTO.getQuantity(), cartDTO.getSize()));
        }
    }
}
