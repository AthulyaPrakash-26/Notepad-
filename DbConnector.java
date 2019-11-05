package model;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
public class DbConnector 
{
	 private static DbConnector jdbc;  
	 private DbConnector() { }  
     
	    //Now we are providing global point of access.  
	         public static DbConnector getInstance() {    
	                                     if (jdbc==null)  
	                                   {  
	                                            jdbc=new  DbConnector();  
	                                   }  
	                         return jdbc;  
	             }  
	static String driverClass = "org.postgresql.Driver";
	static String url = "jdbc:postgresql://localhost:5432/ecommerceDB";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(driverClass);
		Connection con = DriverManager.getConnection(url, "postgres", "postgres");
		return con;
	}
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		System.out.println("Connection successful");
		
	}
}
