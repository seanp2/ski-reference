package com.updatedb;

import com.util.Date;

/**
 * Represents a result as it is displayed in an athletes biography.
 * Only consists of metadata of a race. Contains the race date, the discipline,
 * and the race score the athlete received at the race.
 */
public class BioResult {
	private Date raceDate;
	private String discipline;
	private double score;

	/**
	 *
	 * @param raceDate date of the race
	 * @param discipline discipline of the race
	 * @param score athletes race score at the race
	 */
	public BioResult(Date raceDate,
	                 String discipline, double score) {
		this.raceDate = raceDate;
		this.discipline = discipline;
		this.score = score;
	}

	/**
	 *
	 * @return the date of the race
	 */
	public Date getDate() {
		return raceDate;
	}

	/**
	 *
	 * @return the score the athlete received at the race
	 */
	public double getScore() {
		return score;
	}

	/**
	 *
	 * @return the discipline of the race
	 */
	public String getDiscipline() {
		return discipline;
	}


}
