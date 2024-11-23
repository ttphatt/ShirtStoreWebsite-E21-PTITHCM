package com.shirtstore.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shirtstore.entity.Users;

public class UserDAO extends JPADAO<Users> implements GenericDAO<Users> {
	
	public UserDAO() {
	}
	
	//Thêm 1 user mới
	public Users create(Users user) {
		return super.create(user);
	}
	
	//Cập nhật user
	@Override
	public Users update(Users user) {
		return super.update(user);
	}

	//Lấy user ra
	@Override
	public Users get(Object userId) {
		return super.find(Users.class, userId);
	}

	//Xóa user
	@Override
	public void delete(Object userId) {
		super.delete(Users.class, userId);
	}

	//Lấy ra tập các user
	@Override
	public List<Users> listAll() {
		return super.findWithNamedQuery("Users.findAll");
	}

	//Đếm số lượng user
	@Override
	public long count() {
		return super.countWithNamedQuery("Users.countAll");
	}
	
	//Tìm user theo email
	public Users findByEmail(String email) {
		List<Users> listUsers = super.findWithNamedQuery("Users.findByEmail", "email", email);
		
		if(listUsers != null && listUsers.size() > 0) {
			return listUsers.get(0);
		}
		return null;
	}

	//Kiểm tra user này đã có tài khoản trong database hay chưa
	public boolean checkLogin(String email, String password) {
		Map<String, Object>param = new HashMap<String, Object>();
		param.put("email", email);
		param.put("password", password);
		
		List<Users> listUsers = super.findWithNamedQuery("Users.checkLogin", param);
		
		if(listUsers.size() == 1) {
			return true;
		}
		return false;
	}
}
