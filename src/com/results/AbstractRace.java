package com.results;

import com.updatedb.DBconnection;
import com.util.Date;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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
	private Double[] scoreMinusPoints;



	public AbstractRace(Document page) throws IllegalArgumentException, IOException {
		this.page = page;
		this.event = getEventAcronym(page.select(".event-header__kind").first().ownText());
		if (event.equals("SG") || event.equals("DH")) {
			this.twoRunRace = false;
		} else {
			this.twoRunRace = true;
		}
		this.venue = page.select("div h1").first().ownText();
		String dateAsText = page.select("time span").first().ownText();
		this.date = Date.monthAsLetters(dateAsText);
		this.competitorIDs = new ArrayList<>();
		initCompetitorIDS();
		ArrayList<String> names = this.getNames();
		this.results = new ArrayList<>();
		this.initAthletes( names, competitorIDs);
		this.penalty = this.results.get(0).getResult().getScore();
	}


	public static String getEventAcronym(String fullEventName) {
		String eventNamewithoutGender;
		if (fullEventName.substring(0,1).equals("M")) {
			eventNamewithoutGender = fullEventName.substring(6);
		} else if (fullEventName.substring(0,1).equals("L")) {
			eventNamewithoutGender = fullEventName.substring(8);

		} else {
			throw new IllegalArgumentException("Invalid event name");
		}
		if (eventNamewithoutGender.equals("Slalom")) {
			return "SL";
		} else if (eventNamewithoutGender.equals("Giant Slalom")) {
			return "GS";
		} else if (eventNamewithoutGender.equals("Super G"))  {
			return "SG";
		} else if (eventNamewithoutGender.equals("Downhill"))  {
			return "DH";
		} else {
			throw new IllegalArgumentException("Invalid event name");
		}
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
			for (int m = 0; m < athleteIndexes.size() + 1; m ++) {
				try {
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
				} catch(ArrayIndexOutOfBoundsException e) {
					// *** Without this some races are s.t the last dnf does not receive prev Fis points
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return racersWhoScored;
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

		Elements birthYearsOnPage = page.select(".justify-sb :nth-child(5)");
		for (Element birthYearDiv: birthYearsOnPage) {
			birthYears.add(birthYearDiv.ownText());
		}

		// The first 5 countries that come up with this selector are those of race officials
		// Thus, they should not be considered when initializing the athletes
		Elements countryNamesOnPage = page.select(".country__name-short");
		for (int i = 0; i < countryNamesOnPage.size(); i ++) {
			if (i > 4 ) {
				countries.add(countryNamesOnPage.get(i).ownText());
			}
		}

		Elements run1OnPage = page.select("#events-info-results .hidden-xs:nth-child(7)");
		for (Element run1Div : run1OnPage) {
			run1Times.add(run1Div.ownText());
			if (!twoRunRace) {
				combinedTimes.add(run1Div.ownText());
			}
		}

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

		Elements diffTimesOnPage = page.select("#events-info-results .g-xs-5");
		// The winner has a differential time of 0.0 seconds
		// But on a FIS result page is listed as an empty string
		for (int i = 0; i < diffTimesOnPage.size(); i++) {
			if (i == 0) {
				diffTimes.add("0.00");

			} else {

				diffTimes.add(diffTimesOnPage.get(i).ownText().substring(1));
			}
		}

		Elements fisPointsOnPage = page.select("#events-info-results .g-xs-3.justify-right");
		for (int i = 0; i < fisPointsOnPage.size(); i++) {

			fisPoints.add(fisPointsOnPage.get(i).ownText());
		}
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

	public Double[] getScoreMinusPoints() {
		Double[] scoreMinusPoints = new Double[results.size()];
		for (int i = 0; i < this.results.size(); i++) {
			RaceAthlete athlete = results.get(i);
			if (athlete.getResult().getScore() == 990 ) {
				break;
			}
			if ( athlete.getPreviousPoints() != 990) {
				scoreMinusPoints[i] = athlete.getResult().getScore() - athlete.getPreviousPoints();
				System.out.println(scoreMinusPoints[i]);
			}
		}
		return scoreMinusPoints;
	}

	@Override
	public ArrayList<RaceAthlete> getResults() {
		return results;
	}

	@Override
	public double pointsPerSecond() {
		ArrayList<Result> endResults = new ArrayList<>();
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


	private void initCompetitorIDS() {
		Elements rows = this.page.select(".table-row");
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
	}


	private ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<>();
		Elements namesOnPage = page.select(".justify-left.bold");
		for (int i = 0; i < namesOnPage.size(); i++) {
			String name = namesOnPage.get(i).ownText();
			if (name.substring(0, 2).equals(name.substring(0, 2).toUpperCase())) {
				names.add(namesOnPage.get(i).ownText());
			}
		}
		return names;
	}

}
