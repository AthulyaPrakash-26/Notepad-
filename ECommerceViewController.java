package controller;

import java.util.List;

import org.postgresql.util.PSQLException;

import dao.CartDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Cart;
import model.Order;
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
	
//	public static List<Product> viewAllProduct() throws Exception {
//		List<Product> list = ProductDAO.viewProduct();
//		return list;
//	}
//	public static List<Product> viewProduct(String pid) throws Exception {
//		List<Product> list = ProductDAO.readById(pid);
//		return list;
//	}
	public static List<Product> viewReadByCategory(String category) throws Exception {
		List<Product> list = ProductDAO.readByCategory(category);
		return list;
	}
	public static String viewReadStock(String name)throws Exception{
		String str =  ProductDAO.readStock(name);
		return str;
	}
	public static String viewReadPrice(String name)throws Exception{
		String price =  ProductDAO.readPrice(name);
		return price;
	}
	
	public static void insertCart(String name,String qnty) throws PSQLException, Exception {
		try {
			CartDAO.insert(name,qnty);
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
	public static void viewDelete(String name) {
		try {
			CartDAO.delete(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void insertOrder(String name,String qnty,String price,String total) throws PSQLException, Exception {
		try {
			OrderDAO.insert(name,qnty,price,total);
		} catch (PSQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	public static List<Order> viewOrderSelect() throws Exception {
		List<Order> list = OrderDAO.selectAllOrder();
		return list;
	}
	

}
