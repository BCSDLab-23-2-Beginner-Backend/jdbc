package bcsdbeginner.jdbc;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class JdbcApplication {
	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}
}
