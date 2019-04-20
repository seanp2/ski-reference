package com.tracking;

import com.util.Date;

public class DailyJob implements Runnable {
	@Override
	public void run() {
		DailyTracking dailyTracking = new DailyTracking(Date.todaysDate());

	}
}
