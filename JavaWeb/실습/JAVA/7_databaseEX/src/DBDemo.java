import java.sql.*;
import java.util.Properties;

public class DBDemo {
	public void connectDB() throws ClassNotFoundException, SQLException{
		final String dbClassname = "com.mysql.jdbc.Driver";
		final String CONNECTION = "jdbc:mysql://localhost:3306/mydb";
		
		System.out.println(dbClassname);
		Class.forName(dbClassname);
		
		Properties p = new Properties();
		p.put("user", "root");
		p.put("password", "thdud439");
		
		Connection c = DriverManager.getConnection(CONNECTION, p);
		System.out.println("DB가 연결되었습니다.");	
	}
}
