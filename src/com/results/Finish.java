package com.results;


public class Finish implements Result {
	int rank;
	double run1Time;
	double run2Time;
	double score;
	double difference;
	int bib;

	public Finish(int rank, int bib, double run1Time, double run2Time, double difference, double score) {
		this.rank = rank;
		this.bib = bib;
		this.run1Time = run1Time;
		this.run2Time = run2Time;
		this.difference = difference;
		this.score = score;
	}

	public Finish(int rank, int bib, double run1Time,  double difference, double score) {
		this.rank = rank;
		this.bib = bib;
		this.run1Time = run1Time;
		this.run2Time = 0.0;
		this.difference = difference;
		this.score = score;
	}

	@Override
	public String getFirstRun() {
		return run1Time + "";
	}

	@Override
	public String getSecondRun() {
		return run2Time + "";
	}

	@Override
	public String getCombined() {
		return run1Time + run2Time + "";
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public int getBib() {
		return this.bib;
	}

	@Override
	public String toString() {
		return "Place: " + rank + " from:" + bib + " Run1:" + this.run1Time + " Run2:" + this.run2Time + " Combined:" + this.getCombined() + " difference = " + difference + " Score: " + score;
 	}

	@Override
	public double getDifference() {
		return this.difference;
  }

	@Override
	public String getRank() {
		return this.rank + "";
	}

}
