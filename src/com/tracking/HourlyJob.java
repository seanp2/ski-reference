package com.tracking;

import com.util.Date;

public class HourlyJob implements Runnable {
	@Override
	public void run() {
		DailyTracking dailyTracking = new DailyTracking(Date.todaysDate());
		dailyTracking.sendNotifications();

	}
}
