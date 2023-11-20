package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static Connection conex = null;
	
	public static Connection getConexao() {
		try {
			conex = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conex;
	}
}
