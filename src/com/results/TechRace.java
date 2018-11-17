package com.results;
import java.io.IOException;

/**
 * Represents a Tech Race with the discipline of either Giant Slalom (GS)
 * or Slalom (SL). Both of these events involve two competitive runs to combine
 * for the final results.
 */
public class TechRace extends AbstractRace {


	/**
	 * @param url url of the result on the FIS webpage
	 * @param event event acronym (either "GS" ,or "SL")
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public TechRace(String url, String event) throws IllegalArgumentException, IOException {
		super(url, event);
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


	@Override
	protected  Result transformResult(String resultsAsString, int factor) {
		String[] splitString = resultsAsString.split("\\s");
		String bib = splitString[1];
		if (factor == 0) {
			String difference = splitString[9];
			if (difference.equals("")) {
				difference = "0.0";
			}
			return new Finish(Integer.parseInt(splitString[0]), Integer.parseInt(bib), AthleteUtils.minutesToSeconds(splitString[6]),
					AthleteUtils.minutesToSeconds(splitString[7]), AthleteUtils.minutesToSeconds(difference),
					Double.parseDouble(splitString[10]));
		} else {
			return new DNF(Integer.parseInt(bib), factor);
		}
	}
}

//	private void initAthletes(Elements tables, ArrayList<String> names, ArrayList<String> competitorIDs ) {
//		this.results = new ArrayList<>();
//		int firstAthleteTableAt = getFirstAthleteRow(tables);
//		this.dnfs = new ArrayList[] {new ArrayList<RaceAthlete>(), new ArrayList<RaceAthlete>()};
//		this.results = new ArrayList<>();
//		int finishFactor = 0;
//		int numBSTables = 0;
//		for (int i = firstAthleteTableAt; i < tables.size(); i++) {
//			String resultsAsString = "";
//			if(!tables.get(i).className().equals("tr-sep")) {
//				for (Element elem : tables.get(i).children()) {
//					resultsAsString += elem.ownText().replaceAll("[^a-zA-Z0-9-:.\\s]", "") + " ";
//				}
//				if (resultsAsString.contains("finish") || resultsAsString.contains( "start")
//						|| resultsAsString.contains( "Disqualified")) {
//					if (resultsAsString.contains("1")) {
//						finishFactor = 1;
//					}
//					else {
//						finishFactor = 2;
//					}
//					numBSTables++;
//				}
//				else {
//					String name = names.get(i - firstAthleteTableAt - numBSTables);
//					String compID = competitorIDs.get(i - firstAthleteTableAt - numBSTables);
//					String[] splitArray = resultsAsString.split("\\s");
//					String birthyear = splitArray[4];
//					String nation = splitArray[5];
//					RaceAthlete racer = new RaceAthlete(Integer.parseInt(compID), name,
//							Integer.parseInt(birthyear), nation,
//							getResult(resultsAsString, finishFactor));
//					results.add(racer);
//					if (finishFactor > 0) {
//						this.dnfs[finishFactor - 1].add(racer);
//					}
//				}
//			}
//			else {
//				numBSTables++;
//			}
//		}
//	}


//	public ArrayList<RaceAthlete> getScorers() {
//		ArrayList<RaceAthlete> racersWhoScored = new ArrayList<>();
//		ArrayList<RaceAthlete> finishResults = new ArrayList<>();
//		for (RaceAthlete athlete : this.results) {
//			if (athlete.getResult() instanceof  Finish) {
//				finishResults.add(athlete);
//			}
//		}
//		int pointsList = AthleteUtils.getPointsList(this.date);
//		Connection connection = new DBconnection().connect();
//		ResultSet rs = null;
//
//		try {
//			String query = "SELECT " + event + "points, Competitorid FROM fispoints" + pointsList;
//			System.out.println(query);
//			Statement stmt = connection.createStatement();
//			rs = stmt.executeQuery(query);
//			ArrayList<String> allPoints = new ArrayList<>();
//			ArrayList<String> allCompIDs = new ArrayList<>();
//			int i = 0;
//			ArrayList<Integer> athleteIndexes = new ArrayList<>();
//			ArrayList<String> compIDS = this.getAllCIDs();
//			while (rs.next()) {
////				System.out.print(rs.getString("lastfirst").length());
////				System.out.println(rs.getString("lastfirst"));
//
//				if (compIDS.indexOf(rs.getString("Competitorid")) >= 0) {
//					athleteIndexes.add(i);
//				}
//				allPoints.add(rs.getString(this.event + "points"));
//				allCompIDs.add(rs.getString("Competitorid"));
//				i++;
//			}
//			ArrayList<Double> originalPoints = new ArrayList<>();
//
//			Double[] sortedPrePoints = new Double[compIDS.size()];
//
//			for (int j = 0 ; j < athleteIndexes.size(); j++) {
//				String id = allCompIDs.get(athleteIndexes.get(j));
//
//				Double points;
//				if (!allPoints.get(athleteIndexes.get(j)).equals("NA")) {
//					points = Double.parseDouble(allPoints.get(athleteIndexes.get(j)));
//				} else {
//					points = 990.0;
//				}
//				if (id == "219231") {
//					System.out.println("\n\n\n" + points);
//				}
//				sortedPrePoints[compIDS.indexOf(id)] = points;
//			}
//			for (int m = 0; m < athleteIndexes.size(); m ++) {
//				if (sortedPrePoints[m] == null) {
//					sortedPrePoints[m] = 990.0;
//				}
//				results.get(m).setPreviousPoints(sortedPrePoints[m]);
//				if (results.get(m).getPreviousPoints() > results.get(m).getResult().getScore()) {
//					racersWhoScored.add(results.get(m));
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return racersWhoScored;
//	}

//	private int getFirstAthleteRow(Elements tables) {
//		int firstAthleteTableAt = 0;
//		boolean breakLoop = false;
//		for (Element j : tables) {
//			if (breakLoop) {
//				break;
//			}
//			for (Element c : j.children()) {
//				if (c.toString().contains("href")) {
//					firstAthleteTableAt = tables.indexOf(j);
//					breakLoop = true;
//					break;
//				}
//			}
//		}
//		return firstAthleteTableAt;
//	}


//	private static Result getResult(String resultsAsString, int factor) {
//		String[] splitString = resultsAsString.split("\\s");
//		String bib = splitString[1];
//		if (factor == 0 && splitString.length == 11) {
//			String difference = splitString[9];
//			if (splitString[9].equals("")) {
//				difference = "0.0";
//			}
//
//			return new Finish(Integer.parseInt(splitString[0]), Integer.parseInt(bib), AthleteUtils.minutesToSeconds(splitString[6]),
//					AthleteUtils.minutesToSeconds(splitString[6]), AthleteUtils.minutesToSeconds(difference),
//					Double.parseDouble(splitString[10]));
//		}
//		else {
//			return new DNF(Integer.parseInt(bib), factor);
//		}
//
//	}


