package customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbutil {
		public static Connection getConnect() throws SQLException, ClassNotFoundException {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
			return con;
		}
	}

