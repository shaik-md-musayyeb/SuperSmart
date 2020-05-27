package model;

import java.time.LocalDate;

public class Orders {
	private int userId;
	private int productId;
	private int quantity;
	private float orderPrice;
	private LocalDate date;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
