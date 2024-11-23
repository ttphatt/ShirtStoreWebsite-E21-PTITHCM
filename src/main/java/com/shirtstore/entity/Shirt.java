package com.shirtstore.entity;
// Generated Apr 20, 2024, 2:20:15 PM by Hibernate Tools 4.3.6.Final


import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "product", catalog = "shirtstoredb", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQueries({
	@NamedQuery(name = "Shirt.findAll", query = "SELECT s FROM Shirt s"),
	@NamedQuery(name = "Shirt.findByShirtName", query = "SELECT s FROM Shirt s WHERE s.shirtName = :shirtName"),
	
	@NamedQuery(name = "Shirt.countAll", query = "SELECT COUNT(*) FROM Shirt"),
	@NamedQuery(name = "Shirt.countByType", query = "SELECT COUNT(s) FROM Shirt s WHERE s.type.typeId = :typeId"),
	
	@NamedQuery(name = "Shirt.findByType", query = "SELECT s FROM Shirt s JOIN Type t ON s.type.typeId = t.typeId AND t.typeId = :typeId"),
	@NamedQuery(name = "Shirt.findNew", query = "SELECT s FROM Shirt s ORDER BY s.releasedDate DESC"),
	
	//Câu lệnh truy vấn để tìm áo theo tên áo hoặc thương hiệu hoặc mô tả bằng keyword
	@NamedQuery(name = "Shirt.search", query = "SELECT s FROM Shirt s WHERE s.shirtName LIKE '%' || :keyword || '%'"
				+ " OR s.brand LIKE '%' || :keyword || '%'"
				+ " OR s.description LIKE '%' || :keyword || '%'"
				+ " OR s.type.typeName LIKE '%' || :keyword || '%'"),
	@NamedQuery(name = "Shirt.countByType2", query = "SELECT COUNT(s.shirtId) FROM Shirt s GROUP BY s.type.typeId"),
	@NamedQuery(name = "Shirt.listSoldShirtName", query = "SELECT s.shirtName FROM Shirt s JOIN OrderDetail od ON s.shirtId = od.shirt.shirtId GROUP BY s.shirtName ORDER BY s.shirtName"),
	@NamedQuery(name = "Shirt.listEachShirtRevenue", query = "SELECT ROUND(SUM(od.subTotal)) FROM Shirt s JOIN OrderDetail od ON s.shirtId = od.shirt.shirtId GROUP BY s.shirtName ORDER BY s.shirtName" )
})
public class Shirt implements java.io.Serializable {

	private int shirtId;
	private Type type;
	private String shirtName;
	private String brand;
	private String description;
	private String shirtImage;
//	private String base64Image;
	private float shirtPrice;
	private Date releasedDate;
	private Warehouse warehouse;
	private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);
	private Set<Rate> rates = new HashSet<Rate>(0);

	public Shirt() {
	}
	
	public Shirt(int shirtId) {
		super();
		this.shirtId = shirtId;
	}

	public Shirt(Type type, String shirtName, String brand, String description, String shirtImage, float shirtPrice,
				 Date releasedDate) {
		super();
		this.type = type;
		this.shirtName = shirtName;
		this.brand = brand;
		this.description = description;
		this.shirtImage = shirtImage;
		this.shirtPrice = shirtPrice;
		this.releasedDate = releasedDate;
	}

	public Shirt(int shirtId, Type type, String shirtName, String brand, String description, String shirtImage,
				 float shirtPrice, Date releasedDate) {
		this.shirtId = shirtId;
		this.type = type;
		this.shirtName = shirtName;
		this.brand = brand;
		this.description = description;
		this.shirtImage = shirtImage;
		this.shirtPrice = shirtPrice;
		this.releasedDate = releasedDate;
	}

	public Shirt(int shirtId, Type type, String shirtName, String brand, String description, String shirtImage,
				 float shirtPrice, Date releasedDate, Set<OrderDetail> orderDetails, Set<Rate> rates) {
		this.shirtId = shirtId;
		this.type = type;
		this.shirtName = shirtName;
		this.brand = brand;
		this.description = description;
		this.shirtImage = shirtImage;
		this.shirtPrice = shirtPrice;
		this.releasedDate = releasedDate;
		this.orderDetails = orderDetails;
		this.rates = rates;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
	public int getShirtId() {
		return this.shirtId;
	}

	public void setShirtId(int shirtId) {
		this.shirtId = shirtId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", nullable = false)
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getShirtName() {
		return this.shirtName;
	}

	public void setShirtName(String shirtName) {
		this.shirtName = shirtName;
	}

	@Column(name = "brand", nullable = false, length = 50)
	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "image")
	public String getShirtImage() {
		return this.shirtImage;
	}

	public void setShirtImage(String shirtImage) {
		this.shirtImage = shirtImage;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public float getShirtPrice() {
		return this.shirtPrice;
	}

	public void setShirtPrice(float shirtPrice) {
		this.shirtPrice = shirtPrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "released_date", nullable = false, length = 10)
	public Date getReleasedDate() {
		return this.releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shirt")
	public Set<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "shirt")
	public Set<Rate> getRates() {
		TreeSet<Rate>sortedRates = new TreeSet<>(new Comparator<Rate>() {

			@Override
			public int compare(Rate rate1, Rate rate2) {
				return rate2.getRateTime().compareTo(rate1.getRateTime());
			}
		});
		
		sortedRates.addAll(rates);
		return sortedRates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}

//	@javax.persistence.Transient
//	public String getBase64Image() {
//		this.base64Image = Base64.getEncoder().encodeToString(this.shirtImage);
//		return this.base64Image;
//	}
//
//	@javax.persistence.Transient
//	public void setBase64Image(String base64Image) {
//		this.base64Image = base64Image;
//	}

	
	@javax.persistence.Transient
	public float getAverageRatingStars() {
		float averageRating = 0.0f;
		float sum = 0.0f;
		
		if(rates.isEmpty()) {
			return 0.0f;
		}
		
		for(Rate rate : rates) {
			sum += rate.getRatingStars();
		}
		
		averageRating = sum / rates.size();
		return averageRating;
	}
	
	@javax.persistence.Transient
	public String getRatingString(float averageRatingStars) {
		String res = "";
		
		int numberOfStars = (int)averageRatingStars;
		
		for(int i = 1; i <= numberOfStars; i++) {
			res += "on,";
		}
		
		int next = numberOfStars + 1;
		
		if(averageRatingStars > numberOfStars) {
			res += "half,";
			next++;
		}
		
		for(int i = next; i <= 5; i++) {
			res += "off,";
		}
		
		return res.substring(0, res.length() - 1);
	}
	
	@javax.persistence.Transient
	public String getRatingStars() {
		float avgRating = getAverageRatingStars();
		
		return getRatingString(avgRating);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(shirtId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shirt other = (Shirt) obj;
		return shirtId == other.shirtId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}
