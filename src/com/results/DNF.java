package com.results;

public class DNF implements Result {
	int bib;
	int runNotFinished;

	public DNF(int bib, int runNotFinished) {
		this.bib = bib;
		this.runNotFinished = runNotFinished;
	}


	@Override
	public String getFirstRun() {
		if (runNotFinished == 1) {
			return "DNF";
		} else {
			return "NA";
		}
	}

	@Override
	public String getSecondRun() {
		if (runNotFinished == 2) {
			return "DNF";
		} else {
			return "NA";
		}
	}

	@Override
	public String getCombined() {
		return "DNF";
	}





	@Override
	public double getScore() {
		return 990.0;
	}

	@Override
	public int getBib() {
		return this.bib;
	}

	@Override
	public double getDifference() {
		return 0.0;
	}


	@Override
	public String getRank() {
		return "NA";
	}
}
