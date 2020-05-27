package model;

public class Products {
	private String name;
	private float pricePerUnit;
	private int quantityAvailable;
	private String measures;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
}
