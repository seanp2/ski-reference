package com.updatedb;

import com.util.Date;

public class BioResult {
	private Date raceDate;
	private String discipline;
	private double score;

	public BioResult(Date raceDate,
	                 String discipline, double score) {
		this.raceDate = raceDate;
		this.discipline = discipline;
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


}
