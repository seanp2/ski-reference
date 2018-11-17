package com.results;
import com.util.Date;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class AthleteUtils {
	Document fispages;
	
	public AthleteUtils() {
	}

	
	/**
	 * Makes sure that the given competitor ID is valid and represents an athlete
	 * @param competitorID the competitor ID of an athlete
	 * @param lastname the last name of the competitor
	 * @return the competitor ID
	 * @throws IllegalArgumentException if the competitor ID is invalid
	 */
	public static int checkCompetitorID(int competitorID, String lastname) throws IllegalArgumentException {
	  Connection bioPage;
		bioPage = Jsoup.connect("https://data.fis-ski.com/dynamic/athlete-biography.html?sector=AL&competitorid="
				  + competitorID);
		try {
			if (bioPage.get().toString().contains("<title>" + lastname)) {
				return competitorID;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(String.format("Competitor ID %d is invalid", competitorID));
		}

		return 0;

	}





	public static double minutesToSeconds(String time) {
		if (time.contains(":")) {
			double seconds = Double.parseDouble(time.substring(time.indexOf(":") + 1));
			seconds = seconds + Double.parseDouble(time.substring(0, time.indexOf(":"))) * 60;
			return seconds;
		}
		else {
			return Double.parseDouble(time);
		}
	}


	public static String secondsToMinutes(String time) {
		try {
			double inseconds = Double.parseDouble(time);
			int minutes = 0;
			while (inseconds > 60) {
				inseconds -= 60.0;
				minutes++;
			}
			DecimalFormat myFormat = new DecimalFormat("00.00");
			return minutes + ":" + myFormat.format(inseconds);
		}
		catch (Exception e) {
			return time;
		}
	}

	/**
	 * Visit https://data.fis-ski.com/alpine-skiing/fis-points-lists.html
	 * which displays all of the time durations and the lists they correspond to
	 * @param date the date
	 * @return the FIS points list which the given date corresponds to
	 */
//	public int getPointsList(Date date) {
//		Document fispages = null;
//		try {
//			this.fispages = Jsoup.connect("https://data.fis-ski.com/alpine-skiing/fis-points-lists.html").get();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Elements tables = fispages.select(".info_clip , #fis-points-list-body .g-xs-24");
//		ArrayList<String> textInfo = new ArrayList<>();
//		for(Element i : tables)  {
//			for (Element k : i.children()) {
//				textInfo.add(k.ownText());
//			}
//		}
//		for (int i = 0; i < textInfo.size(); i++) {
//			try {
//				Date startDate = new Date(textInfo.get(i).substring(0,10));
//				Date endDate = new Date(textInfo.get(i + 1).substring(0,10));
//				if (date.sameOrLaterThan(startDate) && endDate.sameOrLaterThan(date)) {
//					try {
//						String accu = fispages.html().substring(fispages.html().indexOf(startDate.toString()));
//						String accu2 = accu.substring(accu.indexOf("listid="));
//						return Integer.parseInt(accu2.substring(7, accu2.indexOf("\"")));
//					} catch(Exception e) {
//						e.printStackTrace();
//					}
//				}
//			} catch(Exception e ) {
//				//NumberParsing or IllegalArgument exceptions will be thrown
//				//by Date constructor
//			}
//		}
//		return 1;
//	}

	public int getPointsList(Date date) {

		try {
			this.fispages = Jsoup.connect("https://www.fis-ski.com/DB/alpine-skiing/fis-points-lists.html").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert fispages != null;
//		Elements rows = fispages.select(".info_clip , #fis-points-list-body .g-xs-24");
		Elements rows = fispages.select("#fis-points-list-body .g-lg-3 , :nth-child(6) .split-row__item:nth-child(1) " +
				":nth-child(2) a, #fis-points-list-body .g-lg-3");
		// index mod 3 = 0 of rows is a start date of a list
		// index mod 3 = 1 of rows is an end date of a list
		// index mod 3 = 2 of rows is the csv link for the list
		String[] rowAccu = new String[2];
		for (int i = 0; i < rows.size(); i++) {
			if (i % 3 == 0 ) {
				rowAccu[0] = rows.get(i).ownText();

			}
			else if (i % 3 == 1) {
				rowAccu[1] = rows.get(i).ownText();

			}
			else if (i % 3 == 2) {
				if (date.sameOrLaterThan(new Date(rowAccu[0], true)) && new Date(rowAccu[1], true).sameOrLaterThan(date)) {
					String pointsListAsMalformedString = rows.get(i).attr("onclick").substring(42);
					int pointsList = Integer.parseInt(pointsListAsMalformedString
							.substring(0, pointsListAsMalformedString.length() - 2));
					return pointsList;
				}
				else {
					rowAccu = new String[2];
				}
			}
		}



//		for (int i = 0; i < rows.size(); i++) {
//			Element row = rows.get(i);
//			Date startDate = new Date(row.select("#fis-points-list-body .hidden-sm-down").get(0).ownText());
//			Date endDate = new Date(row.select("#fis-points-list-body .hidden-sm-down").get(1).ownText());
//			if (date.sameOrLaterThan(startDate) && endDate.sameOrLaterThan(date)){
//				Element csvDiv = row.select(".split-row__item:nth-child(1) :nth-child(2) .link__text").first();
//				System.out.println(csvDiv);
//			}
//
//
//		}


		return 1;

	}




	public String  getRaceID(String url) {
		String raceid = "-1";
		Scanner scanner = new Scanner(new StringReader(url));
		scanner.useDelimiter("&");
		while (scanner.hasNext()) {
			String parameter = scanner.next();
			if (parameter.substring(0, 7).equals("raceid=")) {
				raceid = parameter.substring(7);
			}
		}
//		String raceID = url.substring(url.indexOf("raceid=") + 7);
//		try {
//			Integer.parseInt(raceID);
//		}catch (NumberFormatException e) {
//			throw new IllegalArgumentException("Invalid URL");
//		}
		if (raceid.equals("-1")) {
			throw new IllegalArgumentException("Invalid URL");
		}
		return raceid;
	}

}
