package model;

public class Product {
	
	private String productId;

	private String productName;

	private String category;

	private String price;

	private String stock;

	public Product(String productId, String name, String category, String price, String stock) {
		super();
		this.productId = productId;
		this.productName = name;
		this.category = category;
		this.price = price;
		this.stock = stock;
	}

	public Product(String name, String price) {
		this.productName = name;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return productName;
	}

	public void setName(String name) {
		this.productName = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	

	
}

