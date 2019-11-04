package dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Cart;
import model.DbConnector;
import model.Product;

public class CartDAO {

	private static String CREATESQL = "create table ProductCart (productId CHARACTER VARYING(150) NOT NULL ,Quantity CHARACTER VARYING(150))";
	private static String INSERTSQL = "insert into ProductCart values(?,?)";
	private static String SELECTSQL 	= "select * from ProductCart";

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

	public static void insert(String id,String qnty) throws Exception {

		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();

			pStmt = con.prepareStatement(INSERTSQL);
			pStmt.setString(1, id );
			pStmt.setString(2, qnty);	
			pStmt.executeUpdate();

		} catch (Exception ex) {
			throw ex;

		} finally {

			pStmt.close();
			con.close();

		}
	}
	public static List<Cart> selectAll() throws Exception {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(SELECTSQL);

			ResultSet rs = pStmt.executeQuery();
			ArrayList<Cart> proList = new ArrayList<>();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				Cart cart = new Cart(rs.getString(1), rs.getString(2));
				proList.add(cart);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return proList;
		} catch (Exception ex) {
			throw ex;

		} finally {
			pStmt.close();
			con.close();
		}
	}
	
	public static void main(String args[]) {
		try {
		//	 create();
		//	insert(new Cart("101", "asdasd"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
