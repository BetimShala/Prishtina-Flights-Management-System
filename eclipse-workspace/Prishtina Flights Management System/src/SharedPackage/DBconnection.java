package SharedPackage;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBconnection 
{
	
	public static Connection sqlConnector()
	{
		try 
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pfms","root","123456");
			return connection;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		
	}

}
