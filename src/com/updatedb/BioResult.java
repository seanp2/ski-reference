package com.updatedb;

import com.util.Date;

public class BioResult {
	private Date raceDate;
	private String venue;
	private String nation;
	private String category;
	private String discipline;
	private String rank;
	private double score;

	public BioResult(Date raceDate,
	                 String discipline, double score) {
		this.raceDate = raceDate;

		this.category = category;
		this.discipline = discipline;
		this.rank = rank;
		this.score = score;
	}


	public Date getDate() {
		return raceDate;
	}

	public double getScore() {
		return score;
	}

	public String getDiscipline() {
		return discipline;
	}

	@Override
	public String toString() {
		return "Race Date: " + this.raceDate + " Venue: " + this.venue + " Category: " + this.category
		+ " discipline: " + this.discipline + " Rank: " + this.rank + " Score: " + this.score;
	}
}
