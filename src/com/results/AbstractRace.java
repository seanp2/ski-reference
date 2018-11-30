package com.results;

import com.updatedb.DBconnection;
import com.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractRace implements Race {
	private Document page;
	protected Date date;
	protected ArrayList<RaceAthlete> results;
	private double penalty;
	private boolean twoRunRace;
	private ArrayList<String> competitorIDs;
	private String event;
	private String venue;
	private ArrayList[] dnfs;



	public AbstractRace(String url, String event) throws IllegalArgumentException, IOException {
		this.event = event;
		if (!url.substring(0,63).equals("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=")) {
			throw new IllegalArgumentException("URL does not lead to a valid FIS race result");
		}
		if (Arrays.asList( new String[]{"DH", "SG", "SL", "GS", "AC"}).indexOf(event) == -1) {
			throw new IllegalArgumentException("Invalid Event name");
		}

		if (event.equals("SG") || event.equals("DH")) {
			this.twoRunRace = false;
		} else {
			this.twoRunRace = true;
		}

		this.page = Jsoup.connect(url).get();

		Elements tables = page.select(".table_min_height div");
		this.venue = page.select("div h1").first().ownText();
		String dateAsText = page.select("time span").first().ownText();
		this.date = Date.monthAsLetters(dateAsText);
		this.competitorIDs = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		String nameSelector;
		if (twoRunRace) {//g-lg-8 g-md-8 g-sm-7 g-xs-8
			nameSelector = "justify-left bold";
		} else {
			nameSelector = "g-lg-12 g-md-12 g-sm-11 g-xs-8 justify-left bold";
		}


		Elements rows = page.select(".table-row");
		for (int i = 0; i < rows.size(); i++) {
			Element row = rows.get(i);
			if (row.hasAttr("href")) {
				String athleteLink = row.attr("href");
				if (athleteLink.contains("competitorid=")) {
					String compID = athleteLink.substring(athleteLink.indexOf("competitorid=") + "competitorid=".length());
					competitorIDs.add(compID);
				}
			}
		}
		System.out.println("COMPIDS: " + competitorIDs.size());
		Elements namesOnPage = page.select(".justify-left.bold");
		for (int i = 0; i < namesOnPage.size() ; i++) {
			String name = namesOnPage.get(i).ownText();
			if (name.substring(0, 2).equals(name.substring(0,2).toUpperCase())) {
				System.out.println(namesOnPage.get(i).ownText());
				names.add(namesOnPage.get(i).ownText());
			}


		}

		System.out.println("NAMES " + names.size());

//		page.getElementsByClass(".justify-left.bold")
//				.forEach(elem -> {
//					String dataLink = elem.attr("data-link");
//					System.out.println(dataLink.substring(dataLink.indexOf("competitorid=") + "competitorid=".length()));
//					competitorIDs.add(dataLink.substring(dataLink.indexOf("competitorid=") + "competitorid=".length()));});

		this.results = new ArrayList<>();
		this.initAthletes( names, competitorIDs);
		this.penalty = this.results.get(0).getResult().getScore();
	}




	@Override
	public ArrayList<RaceAthlete> getScorers() {
		ArrayList<RaceAthlete> racersWhoScored = new ArrayList<>();
		int pointsList = new AthleteUtils().getPointsList(this.date);
		Connection connection = new DBconnection().connect();
		ResultSet rs;
		try {
			String query = "SELECT " + this.event + "points, " + this.event + "high, " + this.event + "low" +
					", Competitorid FROM fispoints" + pointsList;
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			ArrayList<Double> allPoints = new ArrayList<>();
			ArrayList<Double> allHighs = new ArrayList<>();
			ArrayList<String> allCompIDs = new ArrayList<>();
			int i = 0;
			ArrayList<Integer> athleteIndexes = new ArrayList<>();
			ArrayList<String> compIDS = this.competitorIDs;
			while (rs.next()) {
				if (compIDS.indexOf(rs.getString("Competitorid")) >= 0) {
					athleteIndexes.add(i);
				}
				double high = Double.parseDouble(rs.getString(event + "high"));
				double low = Double.parseDouble(rs.getString(event + "low"));
				double points = 990.0;
				if (!rs.getString(this.event + "points").equals("NA") &&
						!rs.getString(this.event + "points").equals( "")) {
					points = Double.parseDouble(rs.getString(this.event + "points"));
				}
				if (Math.abs(((high + low) / 2) - points) < 0.02) {
					allHighs.add(high);
				} else {
					allHighs.add(points);
				}
				allPoints.add(points);
				allCompIDs.add(rs.getString("Competitorid"));
				i++;
			}
			Double[] sortedPrePoints = new Double[compIDS.size()];
			Double[] sortedHighPoints = new Double[compIDS.size()];
			for (int j = 0 ; j < athleteIndexes.size(); j++) {
				String id = allCompIDs.get(athleteIndexes.get(j));
				Double points = allPoints.get(athleteIndexes.get(j));
				sortedHighPoints[compIDS.indexOf(id)] = allHighs.get(athleteIndexes.get(j));
				sortedPrePoints[compIDS.indexOf(id)] = points;
			}
			for (int m = 0; m < athleteIndexes.size(); m ++) {
				if (sortedPrePoints[m] == null) {
					sortedPrePoints[m] = 990.0;
				}
				if (sortedHighPoints[m] == null) {
					sortedHighPoints[m] = 990.0;
				}
				results.get(m).setPreviousPoints(sortedPrePoints[m]);
				if (sortedHighPoints[m] > results.get(m).getResult().getScore()) {
					racersWhoScored.add(results.get(m));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return racersWhoScored;
	}

	/**
	 * Finds the index of the results table within the larger list of tables
	 * of the FIS result web page
	 * @param tables the complete list of tables of the webpage
	 * @return the index of results table
	 */
	private int getFirstAthleteRow(Elements tables) {
		int firstAthleteTableAt = 0;
		boolean breakLoop = false;
		for (Element j : tables) {
			if (breakLoop) {
				break;
			}
			for (Element c : j.children()) {
				if (c.toString().contains("href")) {
					firstAthleteTableAt = tables.indexOf(j);
					breakLoop = true;
					break;
				}
			}
		}
		return firstAthleteTableAt;
	}


	/**
	 * Initializes the results of race.
	 * For each row in the results table on the FIS webpage,
	 * adds a RaceAthlete to represent an individual race result.
	 * A RaceAthlete contains all relevant information such as Name, result information,
	 * rank, and score.
	 * The resulting list of RaceAthlete is sorted by rank of racers in the competition.
	 * @param names all of the names of the athletes
	 * @param competitorIDs all of the competitor id's of the athletes
	 */
	private void initAthletes( ArrayList<String> names, ArrayList<String> competitorIDs) {
		ArrayList<String> bibs = new ArrayList<>();
		ArrayList<String> birthYears = new ArrayList<>();
		ArrayList<String> countries = new ArrayList<>();
		ArrayList<String> run1Times = new ArrayList<>();
		ArrayList<String> run2Times = new ArrayList<>();
		ArrayList<String> combinedTimes = new ArrayList<>();
		ArrayList<String> diffTimes = new ArrayList<>();
		ArrayList<String> fisPoints = new ArrayList<>();

		Elements bibOnPage = page.select(".g-sm-1.gray");
		for (Element bibDiv: bibOnPage) {
			bibs.add(bibDiv.ownText());
		}
		System.out.println("BIBS: " + bibs.size());

		Elements birthYearsOnPage = page.select(".justify-sb :nth-child(5)");
		for (Element birthYearDiv: birthYearsOnPage) {

			birthYears.add(birthYearDiv.ownText());
		}

		System.out.println("BirthYears: " + birthYears.size());

		// The first 5 countries that come up with this selector are those of race officials
		// Thus, they should not be considered when initializing the athletes
		Elements countryNamesOnPage = page.select(".country__name-short");
		for (int i = 0; i < countryNamesOnPage.size(); i ++) {
			if (i > 4 ) {

				countries.add(countryNamesOnPage.get(i).ownText());
			}

		}

		System.out.println("Nations: " + countries.size());

		Elements run1OnPage = page.select("#events-info-results .hidden-xs:nth-child(7)");
		for (Element run1Div : run1OnPage) {
			run1Times.add(run1Div.ownText());
			if (!twoRunRace) {
				combinedTimes.add(run1Div.ownText());
			}
		}

		System.out.println("RUN 1: " + run1Times.size());

		if (twoRunRace) {
			Elements run2OnPage = page.select("#events-info-results .hidden-xs:nth-child(8)");
			for (Element run2Div : run2OnPage) {
				run2Times.add(run2Div.ownText());
			}

			Elements combinedTimesOnPage = page.select("#events-info-results .hidden-xs:nth-child(9)");
			for (int i = 0; i < combinedTimesOnPage.size(); i++) {
				combinedTimes.add(combinedTimesOnPage.get(i).ownText());
			}
		}
		System.out.println("COMBINED TIMES: "+ combinedTimes.size());


		Elements diffTimesOnPage = page.select("#events-info-results .g-xs-5");
		// The winner has a differential time of 0.0 seconds
		// But on a FIS result page is listed as an empty string
		for (int i = 0; i < diffTimesOnPage.size(); i++) {
			if (i == 0) {
				diffTimes.add("0.00");
				System.out.println("0.00");
			} else {

				diffTimes.add(diffTimesOnPage.get(i).ownText().substring(1));
			}
		}
		System.out.println("diff times: " + diffTimes.size());

		Elements fisPointsOnPage = page.select("#events-info-results .g-xs-3.justify-right");
		for (int i = 0; i < fisPointsOnPage.size(); i++) {

			fisPoints.add(fisPointsOnPage.get(i).ownText());
		}
		System.out.println("fis points: " + fisPoints.size());

		//The size of combinedTimes is the number of racers who have
		//successfully finished the race
		for (int i = 0; i < combinedTimes.size(); i++) {
			Finish resultOfAthlete;
			if (twoRunRace) {
				 resultOfAthlete = new Finish(i + 1, Integer.parseInt(bibs.get(i)),
						 AthleteUtils.minutesToSeconds(run1Times.get(i)), AthleteUtils.minutesToSeconds(run2Times.get(i)),
						 AthleteUtils.minutesToSeconds(diffTimes.get(i)), Double.parseDouble(fisPoints.get(i)));
			} else {
				resultOfAthlete = new Finish(i + 1, Integer.parseInt(bibs.get(i)),
						AthleteUtils.minutesToSeconds(run1Times.get(i)),
						AthleteUtils.minutesToSeconds(diffTimes.get(i)), Double.parseDouble(fisPoints.get(i)));
			}
			try {
				RaceAthlete athlete = new RaceAthlete(Integer.parseInt(competitorIDs.get(i)), names.get(i),
						Integer.parseInt(birthYears.get(i)), countries.get(i), resultOfAthlete);
				this.results.add(athlete);
			} catch  (NumberFormatException e) {

			}



		}
		this.dnfs = new ArrayList[] {new ArrayList<RaceAthlete>(), new ArrayList<RaceAthlete>()};
		for (int i = combinedTimes.size(); i < competitorIDs.size(); i++) {
			DNF result  = new DNF(Integer.parseInt(bibs.get(i)), 1);
			RaceAthlete athlete = new RaceAthlete(Integer.parseInt(competitorIDs.get(i)), names.get(i),
					Integer.parseInt(birthYears.get(i)), countries.get(i), result);
			this.results.add(athlete);
			this.dnfs[0].add(athlete);
		}



	}


	@Override
	public ArrayList<RaceAthlete> getResults() {
		return results;
	}

	@Override
	public double pointsPerSecond() {
		ArrayList<Result> endResults = new ArrayList<>();
		System.out.println(this.results == null);
		for (int i = 0; i < this.results.size(); i++) {
			endResults.add(results.get(i).getResult());
		}
		ArrayList<Double> pps = new ArrayList<>();
		for (int i = 1; i < endResults.size(); i++) {
			if (endResults.get(i) instanceof Finish) {
				pps.add((endResults.get(i).getScore() - this.penalty) / ((Finish) endResults.get(i)).getDifference());
			}
		}
		double total = 0;
		for (double num : pps) {
			total += num;
		}
		return total / pps.size();
	}



	@Override
	public Date getDate() {
		return this.date;
	}

	/**
	 * Recieves a string representing a result from an individual athlete.
	 * The formatted String contains all text information from a row
	 * in an FIS alpine result table.
	 * The factor represents the type of result, whether it is full completion,
	 * or did not finish run 1 or run 2.
	 * The factor argument is necessary because specific information regarding
	 * whether the athlete completed the race, did not finish run 1, or
	 * did not finish run 2 is not available in an individual row.
	 *
	 * Finish Factor Guide:
	 *    factor = 0 : full race completion
	 *    factor = 1 : did not finish run one
	 *    factor = 1 : did not finish run two
	 *
	 * @param resultsAsString the text information in a row of a FIS competition results table
	 * @param factor the Finish Factor of the individual results
	 * @return the Result holding the relevant finish information
	 */
	protected abstract Result transformResult(String resultsAsString, int factor);


	/**
	 * @return an ArrayList of competitor id's for every athlete in the race
	 */




	@Override
	public String getVenue() {
		if (this.venue.contains(",")) {
			return this.venue.substring(0, venue.indexOf(","));
		} else {
			return this.venue;
		}
	}

	@Override
	public double getFinishRate() {
		int totalDNFS = 0;
		for (ArrayList<RaceAthlete> dnflist : dnfs) {
			totalDNFS += dnflist.size();
		}
		return (double) (results.size() - totalDNFS) / results.size();
	}


	/**
	 * Finds the athlete that moved up in the results the farthest
	 * from their bib start position.
	 * Incredibly unlikely that no one would move up.
	 * @return the athlete who moved up the farthest
	 */
	@Override
	public RaceAthlete attackFromTheBack() throws NullPointerException {
		int biggestJump = 0;
		RaceAthlete curAthlete = null;
		for (int i = 0; i < this.results.size(); i++) {
			Result athleteResult = this.results.get(i).getResult();
			if (!athleteResult.getCombined().contains("DNF")) {
				int jump = athleteResult.getBib() - Integer.parseInt(athleteResult.getRank());
				if (jump > biggestJump) {
					biggestJump = jump;
					curAthlete = this.results.get(i);
				}
			}
		} if (curAthlete == null) {
			throw new NullPointerException("No one moved up in this race. Hmmm, interesting");
		}
		return curAthlete;
	}
}
