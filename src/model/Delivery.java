package model;

import java.time.LocalDate;

public class Delivery {
	private int orderId;
	private LocalDate date_of_delivery;
	private String received_by;
	private int boy_id;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public LocalDate getDate_of_delivery() {
		return date_of_delivery;
	}
	public void setDate_of_delivery(LocalDate date_of_delivery) {
		this.date_of_delivery = date_of_delivery;
	}
	public String getReceived_by() {
		return received_by;
	}
	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}
	public int getBoy_id() {
		return boy_id;
	}
	public void setBoy_id(int boy_id) {
		this.boy_id = boy_id;
	}
	
}
