package com.shirtstore.controller.frontend.cart;

import java.util.*;

import com.shirtstore.dao.JPADAO;
import com.shirtstore.dao.ShirtDAO;
import com.shirtstore.entity.Cart;
import com.shirtstore.entity.CartDTO;
import com.shirtstore.entity.Shirt;

public class ShoppingCart {
	private List<CartDTO> carts;
	private ShirtDAO shirtDAO;

	public ShoppingCart() {
		shirtDAO = new ShirtDAO();
		carts = new ArrayList<>();
	}



	//Thêm giày vào giỏ hàng
	public void addItem(Shirt shirt, String size) {
		boolean found = false;
		for(CartDTO cart : carts){
			if(cart.getShirt().getShirtId() == shirt.getShirtId() && cart.getSize().equals(size)){
				cart.setQuantity(cart.getQuantity() + 1);
				found = true;
				break;
			}
		}

		if(!found){
			carts.add(new CartDTO(0, shirt, 1, size));
		}
	}

	public List<CartDTO> getCarts() {
		return this.carts;
	}

	public void setCarts(List<CartDTO> carts) {
		this.carts = carts;
	}
	
	//Bỏ áo ra khỏi giỏ hàng
	public void removeItem(int shirtId, String size) {
		for(CartDTO cart : carts){
			if(cart.getShirt().getShirtId() == shirtId && cart.getSize().equals(size)){
				carts.remove(cart);
				break;
			}
		}
	}
	
	//Tính tổng số lượng hàng có trong giỏ hàng
	public int getTotalQuantity() {
		int totalQuantity = 0;

		for(CartDTO cart : carts){
			totalQuantity += cart.getQuantity();
		}

		return totalQuantity;
	}
	
	//Tính tổng giá tiền của giỏ hàng
	public float getTotalAmount() {
		float total = 0.0f;

		for (CartDTO cart : carts) {
			total += cart.getShirt().getShirtPrice() * cart.getQuantity();
		}

		return total;
	}
	
	//Loại bỏ hết sản phẩm trong giỏ hàng
	public void clearCart(){
		carts.clear();
	}

	public void deleteCart(int customer_id){
		new JPADAO<>().deleteCart(customer_id);
	}
	
	//Tìm tổng số lượng sản phẩm có trong giỏ hàng

	public int getTotalItems() {
		return carts.size();
	}
	
	//Cập nhật lại giỏ hàng
	public void updateCart(int[] shirtIds, int[] quantities, String[] sizes) {
		carts.clear();
		for (int i = 0; i < shirtIds.length; i++) {
			Shirt shirt = shirtDAO.get(shirtIds[i]);
			carts.add(new CartDTO(0, shirt, quantities[i], sizes[i]));
		}

		for(int i = 0; i < carts.size() - 1; i++){
			for(int j = i + 1; j < carts.size(); j++){
				if(carts.get(i).getShirt().getShirtId() == carts.get(j).getShirt().getShirtId() && carts.get(i).getSize().equals(carts.get(j).getSize())){
					carts.get(i).setQuantity(carts.get(i).getQuantity() + carts.get(j).getQuantity());
					carts.remove(j);
					j--;
				}
			}
		}
	}
}
