package com.results;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RaceTest {



	@Test
	public void test0() {
		try {
			Race sugarbowl = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=83689", "GS");
			System.out.println(sugarbowl.getResults().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void test2() {
		try {
			Race stowe2017 = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=87524", "GS");
			RaceAthlete rcs = new RaceAthlete(139503,
					"COCHRAN-SIEGLE Ryan", 1992,  "USA",new Finish(1, 1, 59.65,	 59.71, 0.0,9.57));
			assertEquals(rcs.toString(), stowe2017.getResults().get(0).toString());
			assertEquals(8.2, stowe2017.pointsPerSecond(), 0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Test
	public void test3() {
		try {
			Race stowe2017SL = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=87526", "SL");
			assertEquals(8.2, stowe2017SL.pointsPerSecond(), 0.1);
			assertEquals(0.46, stowe2017SL.getFinishRate(), 0.1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		try {
			Race attitash2018GS = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=87408", "GS");
			assertEquals(7.8, attitash2018GS.pointsPerSecond(), 0.1);
			ArrayList<RaceAthlete> scorers = attitash2018GS.getScorers();
			for (RaceAthlete scorer : scorers) {
				System.out.println(scorer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		@Test
		public void test5() {
			try {
				Race stratton2017Slalom = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=83434", "GS");
				ArrayList<RaceAthlete> scorers = stratton2017Slalom.getScorers();
				for (RaceAthlete scorer : scorers) {
					System.out.println(scorer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@Test
	public void test6() {
		try {
			SpeedRace sugarloaf2017 = new SpeedRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=87353", "DH");
			assertEquals( (63 - 6.0) / 63.0 , sugarloaf2017.getFinishRate(), 0.01);
//			assertEquals(492 , sugarloaf2017.getVerticalDrop());
			assertEquals("RYAN Bobby" , sugarloaf2017.getResults().get(0).getLastfirstName());

			assertEquals(new Finish(12, 49, 65.87, 2.06, 124.88).toString() ,
					sugarloaf2017.getResults().get(11).getResult().toString());
			assertEquals("Sugarloaf" , sugarloaf2017.getVenue());
			assertEquals("ALPERT John", sugarloaf2017.attackFromTheBack().getLastfirstName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void holscherTest() {
		try {
			TechRace dHolscherRace = new TechRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=95425", "GS");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test8() {
		try {
			SpeedRace NorAmSpeed = new SpeedRace("https://data.fis-ski.com/dynamic/results.html?sector=AL&raceid=89262", "SG");
			for( RaceAthlete athlete : NorAmSpeed.getScorers()) {
				System.out.println(athlete);
			}
		} catch (IOException e) {
		}
	}
}