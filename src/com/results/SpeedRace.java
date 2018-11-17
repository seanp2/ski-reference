package com.results;

import com.results.AbstractRace;

import java.io.IOException;

/**
 * Represents a race who's discipline is either Super G (SG)
 * or Downhill (DH). Both of these disciplines consist of only one
 * competitive run.
 */
public class SpeedRace extends AbstractRace {

	/**
	 *
	 * @param url url of the result on the FIS webpage
	 * @param event event acronym (either "GS" ,or "SL")
	 * @throws IOException
	 */
	public SpeedRace(String url, String event) throws IOException {
		super(url, event);
	}

	@Override
	public String asResultsCSV() {
		this.getScorers();
		String csv = "Rank , Bib , CompetitorID , Name , Year , Nation ,  Total Time , Diff. , Prev. FIS Points, Score,\n";
		for (int i = 0; i < this.results.size() ; i++) {
			RaceAthlete athlete = this.results.get(i);
			Result result = this.getResults().get(i).getResult();
			if (result instanceof  Finish) {
				csv += result.getRank() + " , " +
						result.getBib() + ", " +
						athlete.getCompetitorID() + " , " +
						athlete.getLastfirstName() + " , " +
						athlete.getBirthyear() + " , " +
						athlete.getNation() + " , " +
						AthleteUtils.secondsToMinutes(result.getCombined()) + " , +" +
						result.getDifference() + " , " +
						athlete.getPreviousPoints() + " , " +
						result.getScore() + ",\n";
			} else {
				csv += " , " +
						result.getBib() + ", " +
						athlete.getCompetitorID() + " , " +
						athlete.getLastfirstName() + " , " +
						athlete.getBirthyear() + " , " +
						athlete.getNation() + " , " + AthleteUtils.secondsToMinutes(result.getCombined()) +
						", ," + athlete.getPreviousPoints() + " , " +
						result.getScore() + ",\n";
			}
		}
		return csv;
	}


	@Override
	protected   Result transformResult(String resultsAsString, int factor) {
		String[] splitString = resultsAsString.split("\\s");
		String bib = splitString[1];
		if (factor == 0) {
			String difference = splitString[7];
			if (difference.equals("")) {
				difference = "0.0";
			}
			return new Finish(Integer.parseInt(splitString[0]), Integer.parseInt(bib), AthleteUtils.minutesToSeconds(splitString[6]),
						AthleteUtils.minutesToSeconds(difference), Double.parseDouble(splitString[8]));
		}
		else {
			return new DNF(Integer.parseInt(bib), factor);
		}
	}
}
