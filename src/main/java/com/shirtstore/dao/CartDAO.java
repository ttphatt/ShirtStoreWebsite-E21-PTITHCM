package com.shirtstore.dao;

import com.shirtstore.controller.frontend.cart.ShoppingCart;
import com.shirtstore.entity.Cart;
import com.shirtstore.entity.CartDTO;
import com.shirtstore.entity.Shirt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDAO extends JPADAO<Cart> implements GenericDAO<Cart>{
    @Override
    public Cart create(Cart cart) {
        return super.create(cart);
    }

    @Override
    public Cart get(Object id) {
        return super.find(Cart.class, id);
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public Cart update(Cart cart){
        return super.update(cart);
    }

    @Override
    public List<Cart> listAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
