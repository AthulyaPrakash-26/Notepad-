package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import exception.ProductNotFoundException;
import model.Cart;
import model.DbConnector;
import model.Order;

public class OrderDAO {
	private static String CREATESQL = "create table productOrder (productName CHARACTER VARYING(150) NOT NULL ,Quantity CHARACTER VARYING(150),Price CHARACTER VARYING(150),Total CHARACTER VARYING(150) )";
	private static String INSERTSQL = "insert into productOrder values(?,?,?,?)";
	private static String SELECTSQL = "select * from ProductOrder";
	
	public static void create() throws Exception {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			DbConnector connector = DbConnector.getInstance();
			con = connector.getConnection();
			pStmt = con.prepareStatement(CREATESQL);
			pStmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			pStmt.close();
			con.close();
		}
	}
	public static void insert(String name,String qnty,String price,String total) throws Exception {

		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(INSERTSQL);
			pStmt.setString(1, name );
			pStmt.setString(2, qnty);	
			pStmt.setString(3, price );
			pStmt.setString(4, total);	
			pStmt.executeUpdate();

		} catch (Exception ex) {
			throw ex;

		} finally {

			pStmt.close();
			con.close();
			//	javax.swing.JOptionPane.showMessageDialog(dialog, "Product Not found", "ECommerce", 2);
		}
	}
	public static List<Order> selectAllOrder() throws Exception {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(SELECTSQL);

			ResultSet rs = pStmt.executeQuery();
			ArrayList<Order> orderList = new ArrayList<>();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				Order order = new Order(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4));
				orderList.add(order);
			}
			if (!flag) {
				throw new ProductNotFoundException();
			}
			return orderList;
		} catch (Exception ex) {
			throw ex;

		} finally {
			pStmt.close();
			con.close();
		}
	}
	public static void main(String args[]) {
		try {
			// create();
		  //insert(new Cart("101", "asdasd"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
