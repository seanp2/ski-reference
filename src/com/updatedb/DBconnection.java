package com.updatedb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;




public class DBconnection {
	// init database constants

	FileInputStream input;
	Properties properties;
	private String DATABASE_DRIVER = "com.mysql.jdbc.Driver";;
	private String DATABASE_URL = "jdbc:mysql://fispoints.cmqzttoyzcdi.us-east-2.rds.amazonaws.com:" +
			"3306/FIS_database?useUnicode=true&useJDBCCompliant" +
			"TimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true";
	private static final String MAX_POOL = "250"; // set your own limit

	// init connection object
	private Connection connection;

	// create properties
	private Properties getProperties() {
		Properties configProp = new Properties();
		try {
			input = new FileInputStream("resources/config.properties");
			configProp.load(input);
		} catch(IOException e) {
			e.printStackTrace();
		}
		String USERNAME = configProp.getProperty("dbuser");
		String PASSWORD = configProp.getProperty("dbpassword");
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}

	// connect database
	public Connection connect() {
		if (connection == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	// disconnect database
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

