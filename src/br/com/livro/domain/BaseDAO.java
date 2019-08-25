package br.com.livro.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
	public BaseDAO() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	protected Connection getConnection() throws SQLException {
		String url = "jdbc:mariadb://localhost/LIVRO";
		
		Connection conn = DriverManager.getConnection(url, "livro", "livro123");
		return conn;
	}
	
	public static void main(String[] args) throws SQLException {
		BaseDAO base = new BaseDAO();
		
		Connection conn = base.getConnection();
		System.out.println(conn);
	}
}
