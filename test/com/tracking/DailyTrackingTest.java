package com.tracking;

import com.util.Date;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DailyTrackingTest {

	@Test
	public void test1() {
		Date dateOfMyLastRace = new Date(3, 4, 2017);

		DailyTracking dailyTracking = new DailyTracking(dateOfMyLastRace);
		assertEquals(1, dailyTracking.getCompetitorIdToResult().size());
		dailyTracking.sendNotifications();
		//Sends two emails to my two gmail accounts.
	}

	@Test
	public void test2() {
		//I did not finish this race
		Date date = new Date(26, 3, 2017);
		DailyTracking dailyTracking = new DailyTracking(date);
		dailyTracking.sendNotifications();

	}

	@Test
	public void test3() {
		Date date = Date.todaysDate();
		DailyTracking dailyTracking = new DailyTracking(date);
		dailyTracking.sendNotifications();
	}




}