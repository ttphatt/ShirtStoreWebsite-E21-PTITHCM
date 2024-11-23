package com.shirtstore.entity;

import javax.persistence.*;


@Entity
@Table(name = "users", catalog = "shirtstoredb", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({
	@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u ORDER BY u.fullName"),
	@NamedQuery(name = "Users.countAll", query = "SELECT COUNT(*) FROM Users u"),
	@NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
	@NamedQuery(name = "Users.checkLogin", query = "SELECT u FROM Users u WHERE u.email = :email AND u.password = :password"),
})
public class Users {
	private Integer userId;
	private String email;
	private String password;
	private String fullName;
	private String role;
	
	public Users() {
		super();
	}

	public Users(String email, String fullName, String password, String role) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
	}

	public Users(Integer userId, String email, String fullName, String password, String role) {
		super();
		this.userId = userId;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
	}
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "email", length = 50, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
		
	@Column(name = "password_hash", length = 64, nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "role", length = 30, nullable = false)
	public String getRole() { return this.role; }

	public void setRole(String role) { this.role = role; }
}
