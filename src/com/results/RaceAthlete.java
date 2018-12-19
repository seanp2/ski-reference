package com.results;



/**
 * @author seanpomerantz
 * Represents an athlete's performance in a race. Contains their results in the race, their 
 * start position, as well as the information stored in the Athlete class.
 */
public class RaceAthlete {
	private final int birthyear;
	private final String nation;
	private final String lastfirstName;
	private final int competitorID;
	private final Result result;
	private  double previousPoints;





//	private final String run1Time;
//	private final String run2Time;

//	private final double score;
	
	/**
	 *
	 * @param bibNumber The start position of the athlete. Known as the bib number in ski racing.
	 * @param run1Time The first run time result of the athlete. Represented as a String in 
	 * 					the case that the athlete did not finish the run, which would be represented
	 * 					as DNF
	 * @param run2Time The second run time result of the athlete. Represented as a String in 
	 * 					the case that the athlete did not finish the run, which would be represented
	 * 					as DNF
	 * @param place The final position of the athlete in this race, represented as a String in case  
	 * 				the athlete did not finish
	 * @param score The FIS score awarded to the athlete for their result
	 * @throws IllegalArgumentException if the fields run1Time, run2Time, or place are either a string of
	 * 									numbers or the exact string "DNF"
	 * 
	 */
	public RaceAthlete(int competitorID, String lastfirstName,
			int birthyear, String nation, Result result)
			throws IllegalArgumentException {
		this.lastfirstName = lastfirstName;
		this.competitorID =  competitorID;
		this.birthyear = birthyear;
		this.nation = nation;
		this.result = result;
	}


	public double getPreviousPoints() {
		return previousPoints;
	}


	public void setPreviousPoints(double previousPoints) {
		this.previousPoints = previousPoints;
	}

	public Result getResult() {
		return result;
	}

	public int getCompetitorID() {
		return competitorID;
	}

	public String getLastfirstName() {
		return lastfirstName;
	}

	@Override
	public String toString() {
		return lastfirstName + " " + nation + " " + birthyear + " " + this.result.toString() + " Original Points: " + previousPoints;
	}

	public String getNation() {
		return this.nation;
	}

	public String getBirthyear() {
		return this.birthyear + "";
	}

}
