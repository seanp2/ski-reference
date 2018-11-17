import java.io.IOException;

import com.util.Date;
import org.junit.Test;

import com.results.AthleteUtils;

import static org.junit.Assert.assertEquals;


public class TestAthleteUtils {
	@Test(expected = IllegalArgumentException.class)
	public void testCompID1() {
		for (int i = 0; i < 1000000; i++) {
			AthleteUtils.checkCompetitorID(i, "WASHINGTON George");
		}
	}
	
	
	
	@Test
	public void testCompID2() {
		assertEquals(2000, AthleteUtils.checkCompetitorID(2000, "ASPORT"));
		assertEquals(203574, AthleteUtils.checkCompetitorID(203574, "POMERANTZ"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCompID3() {
		AthleteUtils.checkCompetitorID(2001, "ASPORT");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCompID4() {
		AthleteUtils.checkCompetitorID(2000, "ASPORTs");
	}
	@Test
	public void testCompID6() {
		AthleteUtils.checkCompetitorID(2000, "asport");
	}


	
//	@Test
//	public void testSearch( ) {
//		try {
//			assertEquals("", AthleteUtils.searchAthlete(1).toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testDate() {
		Date mydate = new Date(6, 2, 2018);
		assertEquals(260, new AthleteUtils().getPointsList(mydate));
//		Date mydate2 = new Date(1,7,2018);
//		assertEquals(266, AthleteUtils.getPointsList(mydate2));
//		Date mydate3 = new Date(25,1,2000);
//		assertEquals(68, AthleteUtils.getPointsList(mydate3));
	}


	@Test
	public void testRaceID1() {
		assertEquals("92892",
				new AthleteUtils().getRaceID("https://data.fis-ski.com/dynamic/" +
						"results.html?sector=AL&raceid=92892"));
	}


	@Test
	public void testRaceID2() {
		assertEquals("95367",
				new AthleteUtils().getRaceID("https://data.fis-ski.com/dynamic/" +
						"results.html?sector=AL&competitorid=145581&raceid=95367"));
	}

	@Test
	public void testRaceID3() {
		assertEquals("95367",
				new AthleteUtils().getRaceID("https://data.fis-ski.com/dynamic/" +
						"results.html?sector=AL&raceid=95367&competitorid=145581"));
	}

	@Test
	public void testRaceID4() {
		assertEquals("95367",
				new AthleteUtils().getRaceID("https://data.fis-ski.com/dynamic/results.html?se" +
						"ctor=AL&raceid=95367&competitorid=145581"));
	}



	
}
