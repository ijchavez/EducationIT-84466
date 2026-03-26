package com.automation.selenium.clase6.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
public class JdbcConnectionDemo {
	static String dbUrl;
	static String username;
	static String password;
	static Connection con;
 public static void main(String[] args) {
		dbUrl = "jdbc:mysql://localhost:3306/mydb";
		username = "root";
		password = "";
		 try {
			 System.out.println("Conectando a la base de datos..............."+ dbUrl);
			 con=DriverManager.getConnection(dbUrl, username, password);
			 System.out.println("Connection satisfactoria!!!!!!");
			 
		 }
	 	  catch(Exception e) {
	 			e.printStackTrace();
	 			
	  		}
		 
	 }
 
}
