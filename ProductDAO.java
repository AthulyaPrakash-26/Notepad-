package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DbConnector;
import model.Product;

public class ProductDAO {

	private static String CREATESQL = "create table ProductDetails (productId CHARACTER VARYING(150) NOT NULL  PRIMARY KEY , name CHARACTER VARYING(150) , "
			+ "category CHARACTER VARYING(150) , price CHARACTER VARYING(150) ," + "stock CHARACTER VARYING(150))";
	private static String INSERTSQL = "insert into ProductDetails values(?, ? , ?, ?, ?)";
	private static String SELECTSQL = "select * from ProductDetails";
	private static String READBYIDSQL = "select name,price from ProductDetails where productId = ?";
	private static String READBYCATEGORYSQL = "select name,price from ProductDetails where category = ?";
	private static String READSTOCKSQL = "select stock from ProductDetails where name = ?";
	private static String READPRICESQL = "select price from ProductDetails where name = ?";


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

	public static void insert(Product product) throws Exception {

		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(INSERTSQL);
			pStmt.setString(1, product.getProductId());
			pStmt.setString(2, product.getName());
			pStmt.setString(3, product.getCategory());
			pStmt.setString(4, product.getPrice());
			pStmt.setString(5, product.getStock());
			pStmt.executeUpdate();

		} catch (Exception ex) {
			throw ex;

		} finally {

			pStmt.close();
			con.close();

		}
	}

	public static List<Product> viewProduct() throws Exception {
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(SELECTSQL);

			ResultSet rs = pStmt.executeQuery();
			ArrayList<Product> productList = new ArrayList<>();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				Product product = new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				productList.add(product);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return productList;
		} catch (Exception ex) {
			throw ex;

		} finally {
			pStmt.close();
			con.close();
		}
	}
	public static List<Product> readById(String id)throws Exception{
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(READBYIDSQL);
			pStmt.setString(1, id);
			
			ResultSet rs = pStmt.executeQuery();
			ArrayList<Product> proList = new ArrayList<>();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				Product product = new Product(rs.getString(1), rs.getString(2));
				proList.add(product);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return proList;
		}
		catch(Exception ex) {
			throw ex;
		}finally {
			pStmt.close();
			con.close();
		}
	}
	public static List<Product> readByCategory(String category)throws Exception{
		Connection con = null;
		PreparedStatement pStmt = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(READBYCATEGORYSQL);
			pStmt.setString(1, category);
			
			ResultSet rs = pStmt.executeQuery();
			ArrayList<Product> proList = new ArrayList<>();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				Product product = new Product(rs.getString(1), rs.getString(2));
				proList.add(product);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return proList;
		}
		catch(Exception ex) {
			throw ex;
		}finally {
			pStmt.close();
			con.close();
		}
	}
	public static String readStock(String name)throws Exception{
		Connection con = null;
		PreparedStatement pStmt = null;
		String stock = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(READSTOCKSQL);
			pStmt.setString(1, name);
			ResultSet rs = pStmt.executeQuery();
			
			boolean flag = false;
			while (rs.next()) {
				flag = true;
			  stock = rs.getString(1);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return stock;
		}
		catch(Exception ex) {
			throw ex;
		}finally {
			pStmt.close();
			con.close();
		}
	}
	public static String readPrice(String name)throws Exception{
		Connection con = null;
		PreparedStatement pStmt = null;
		String price = null;
		try {
			con = DbConnector.getConnection();
			pStmt = con.prepareStatement(READPRICESQL);
			pStmt.setString(1, name);
			ResultSet rs = pStmt.executeQuery();
			
			boolean flag = false;
			while (rs.next()) {
				flag = true;
			  price = rs.getString(1);
			}
			if (!flag) {
				throw new Exception("{Product Not Found !!!");
			}
			return price;
		}
		catch(Exception ex) {
			throw ex;
		}finally {
			pStmt.close();
			con.close();
		}
	}

	public static void main(String args[]) {
		try {
			// create();
//		List<Product> l=readByCategory("Home Appliances");
//			for(int i=0;i<l.size();i++)
//			{
//			System.out.println("Product: "+l.get(i).getName());
//			}
			String str = readStock("Lenovo");
			System.out.println(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
