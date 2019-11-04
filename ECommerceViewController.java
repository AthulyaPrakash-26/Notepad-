package controller;

import java.util.List;

import org.postgresql.util.PSQLException;

import dao.CartDAO;
import dao.ProductDAO;
import model.Cart;
import model.Product;

public class ECommerceViewController {
	private Product product;

	public ECommerceViewController(Product product) {
		super();
		this.product = product;
	}

	public void insertProduct() throws PSQLException, Exception {
		try {
			ProductDAO.insert(product);
		} catch (PSQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static List<Product> viewAllProduct() throws Exception {
		List<Product> list = ProductDAO.viewProduct();
		return list;
	}
	public static List<Product> viewProduct(String pid) throws Exception {
		List<Product> list = ProductDAO.readById(pid);
		return list;
	}
	public static List<Product> viewReadByCategory(String category) throws Exception {
		List<Product> list = ProductDAO.readByCategory(category);
		return list;
	}
	
	public static void insertCart(String id,String qnty) throws PSQLException, Exception {
		try {
			CartDAO.insert(id,qnty);
		} catch (PSQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	public static List<Cart> viewSelectAll() throws Exception {
		List<Cart> list = CartDAO.selectAll();
		return list;
	}

}
