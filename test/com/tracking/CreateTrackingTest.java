package com.tracking;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreateTrackingTest {

	@Test
	public void test1() {
		ArrayList<Integer> fisIds = new ArrayList<>();
		fisIds.add(1000);
		CreateTracking myTracking = new CreateTracking(fisIds, "seanpomerantz@gmail.com");
		myTracking.updateTrackingDB();
		myTracking.sendConfirmTracking();
	}


}