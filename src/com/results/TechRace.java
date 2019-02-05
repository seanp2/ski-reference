package com.results;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Represents a Tech Race with the discipline of either Giant Slalom (GS)
 * or Slalom (SL). Both of these events involve two competitive runs to combine
 * for the final results.
 */
public class TechRace extends AbstractRace {


	/**
	 * @param page The document of the FIS result web page\
	 * @throws IOException if the web page is invalid
	 */
//	public TechRace(String url, String event) throws IllegalArgumentException, IOException {
	public TechRace(Document page) throws IllegalArgumentException, IOException {
		super(page);
//		super(url, event);
	}


	@Override
	public String asResultsCSV() {
		this.getScorers();
		String csv = "Rank , Bib , CompetitorID , Name, Year , Nation , Run 1, Run 2, " +
				"Total Time , Diff. , Prev. FIS Points, Score,\n";


		for (int i = 0; i < this.results.size(); i++) {
			RaceAthlete athlete = this.results.get(i);
			Result result = this.getResults().get(i).getResult();
			if (result instanceof Finish) {
				csv += result.getRank() + " , " +
						result.getBib() + ", " +
						athlete.getCompetitorID() + " , " +
						athlete.getLastfirstName() + " , " +
						athlete.getBirthyear() + " , " +
						athlete.getNation() + " , " +
						AthleteUtils.secondsToMinutes(result.getFirstRun()) + " , " +
						AthleteUtils.secondsToMinutes(result.getSecondRun()) + " , " +
						AthleteUtils.secondsToMinutes(result.getCombined()) + " , +" +
						result.getDifference() + " , " +
						athlete.getPreviousPoints() + " , " +
						result.getScore() + ",\n";
			} else {
				csv += " , "+ result.getBib() + ", " +
						athlete.getCompetitorID() + " , " +
						athlete.getLastfirstName() + " , " +
						athlete.getBirthyear() + " , " +
						athlete.getNation() + " , , , " + result.getCombined() +
						", ," +
						athlete.getPreviousPoints() + " , " +
						result.getScore() + ",\n";
			}
		}
		return csv;
	}
}


