package model;

public class Cart {
	private String productName;

	private String quantity;

	public Cart(String productName, String quantity) {
		super();
		this.productName = productName;
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductnmae(String productId) {
		this.productName = productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}

