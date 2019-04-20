package com.tracking;

import com.updatedb.DBconnection;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RemoveTrackingTest {

	@Test
	public void test1() {
		DBconnection dBconnection = new DBconnection();
		Connection connection = new DBconnection().connect();
		ArrayList<Integer> fisIDs = new ArrayList<>();
		fisIDs.add(1000);
		CreateTracking createTracking = new CreateTracking(fisIDs, "seanpomerantz@gmail.com");
		createTracking.updateTrackingDB();
		String checkExistsQuery = "SELECT * FROM tracking WHERE fisID = 1000 " +
				"AND email = \"seanpomerantz@gmail.com\";";
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(1, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
		RemoveTracking removeTracking = new RemoveTracking(fisIDs, "seanpomerantz@gmail.com");
		removeTracking.executeRemoveTracking();
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(0, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	// Adds 3 trackings. Removes all trackings.
	@Test
	public void test2() {
		DBconnection dBconnection = new DBconnection();
		Connection connection = new DBconnection().connect();
		ArrayList<Integer> fisIDs = new ArrayList<>();
		fisIDs.add(1000);
		fisIDs.add(2000);
		fisIDs.add(3000);
		CreateTracking createTracking = new CreateTracking(fisIDs, "seanpomerantz@gmail.com");
		createTracking.updateTrackingDB();
		String checkExistsQuery = "SELECT * FROM tracking WHERE email = \"seanpomerantz@gmail.com\";";
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(3, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
		RemoveTracking removeTracking = new RemoveTracking(fisIDs, "seanpomerantz@gmail.com");
		removeTracking.executeRemoveTracking();
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(0, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}


	// Adds 3 trackings. Removes all trackings.
	@Test
	public void test3() {
		DBconnection dBconnection = new DBconnection();
		Connection connection = new DBconnection().connect();
		ArrayList<Integer> fisIDs = new ArrayList<>();
		fisIDs.add(1000);
		fisIDs.add(2000);
		fisIDs.add(3000);
		CreateTracking createTracking = new CreateTracking(fisIDs, "seanpomerantz@gmail.com");
		createTracking.updateTrackingDB();
		String checkExistsQuery = "SELECT * FROM tracking WHERE email = \"seanpomerantz@gmail.com\";";
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(3, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
		ArrayList<Integer> removeFisIDs = new ArrayList<>();
		removeFisIDs.add(1000);
		RemoveTracking removeTracking = new RemoveTracking(removeFisIDs, "seanpomerantz@gmail.com");
		removeTracking.executeRemoveTracking();
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(2, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
		ArrayList<Integer> removeFisIDs2 = new ArrayList<>();
		removeFisIDs2.add(2000);
		removeFisIDs2.add(3000);
		RemoveTracking removeTracking2 = new RemoveTracking(removeFisIDs2, "seanpomerantz@gmail.com");
		removeTracking2.executeRemoveTracking();
		try {
			Statement stmt = connection.createStatement();
			ResultSet  rs1 = stmt.executeQuery(checkExistsQuery);
			int i = 0;
			while (rs1.next()) {
				i++;
			}
			assertEquals(0, i);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}