package com.results;

import java.util.ArrayList;

public interface Result {


	/**
	 * Gets a list of all results from the first run. They will be returned as Strings
	 * in order to account for the DNF result, which represents that an athlete did not
	 * finish the course
	 * @return the list of first run results
	 */
	String  getFirstRun();


	/**
	 * Gets a list of all results from the second run. They will be returned as Strings
	 * in order to account for the DNF result, which represents that an athlete did not
	 * finish the course
	 * @return the list of second run results
	 */
	String getSecondRun();


	String getCombined();

	double getScore();

	int getBib();


	double getDifference();

	String getRank();


}
